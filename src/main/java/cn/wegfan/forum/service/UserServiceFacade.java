package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.model.entity.Board;
import cn.wegfan.forum.model.entity.Permission;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.*;
import cn.wegfan.forum.model.vo.response.UserLoginResponseVo;
import cn.wegfan.forum.model.vo.response.UserRoleResponseVo;
import cn.wegfan.forum.model.vo.response.UserSearchResponseVo;
import cn.wegfan.forum.util.BusinessException;
import cn.wegfan.forum.util.PasswordUtil;
import cn.wegfan.forum.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceFacade {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardAdminService boardAdminService;

    @Autowired
    private CategoryAdminService categoryAdminService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PermissionService permissionService;

    public UserLoginResponseVo login(UserLoginRequestVo requestVo) {
        // TODO: 验证码
        User user = userService.getNotDeletedUserByUsername(requestVo.getUsername());
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.WRONG_USERNAME_OR_PASSWORD);
        }
        // TODO: 判断帐号是否被禁止登录

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), requestVo.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new BusinessException(BusinessErrorEnum.WRONG_USERNAME_OR_PASSWORD);
        }

        // TODO: ip
        userService.updateUserLoginTimeAndIpByUserId(user.getId(), new Date(), "0.0.0.0");

        UserLoginResponseVo responseVo = mapperFacade.map(user, UserLoginResponseVo.class);

        // 设置权限对象
        UserRoleResponseVo roleVo = new UserRoleResponseVo();
        roleVo.setAdmin(user.getAdmin());
        roleVo.setSuperBoardAdmin(user.getSuperBoardAdmin());
        roleVo.setBoardAdmin(boardAdminService.countByUserId(user.getId()) > 0);
        roleVo.setCategoryAdmin(categoryAdminService.countByUserId(user.getId()) > 0);
        responseVo.setPermission(roleVo);

        return responseVo;
    }

    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        // 如果用户本身未登录
        if (subject.getPrincipal() == null) {
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
        subject.logout();
    }

    public void register(UserRegisterRequestVo requestVo) {
        // TODO: 验证码
        User sameUsernameUser = userService.getNotDeletedUserByUsername(requestVo.getUsername());
        if (sameUsernameUser != null) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_USERNAME);
        }

        User user = mapperFacade.map(requestVo, User.class);
        user.setPassword(PasswordUtil.encryptPasswordBcrypt(user.getPassword()));

        userService.addUserByRegister(user);
    }

    public void updatePersonalUserInfo(UpdatePersonalUserInfoRequestVo requestVo) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() == null) {
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
        Long userId = (Long)subject.getPrincipal();
        userService.updateUserPersonalInfoByUserId(userId, requestVo.getNickname(),
                SexEnum.fromValue(requestVo.getSex()), requestVo.getSignature());
    }

    public void updatePersonalPassword(UpdatePersonalPasswordRequestVo requestVo) {
        // TODO: 邮箱验证码
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() == null) {
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
        Long userId = (Long)subject.getPrincipal();
        User currentUser = userService.getNotDeletedUserByUserId(userId);
        if (currentUser == null) {
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
        String correctOldPassword = currentUser.getPassword();
        boolean oldPasswordCorrect = PasswordUtil.checkPasswordBcrypt(requestVo.getOldPassword(), correctOldPassword);
        if (!oldPasswordCorrect) {
            throw new BusinessException(BusinessErrorEnum.WRONG_OLD_PASSWORD);
        }
        String encryptedPassword = PasswordUtil.encryptPasswordBcrypt(requestVo.getNewPassword());
        userService.updateUserPasswordByUserId(userId, encryptedPassword);
        // 删除该用户的其他会话
        SessionUtil.removeSessionsByUserId(userId, true);
        // 用新的密码重新登录
        UsernamePasswordToken token = new UsernamePasswordToken(userId.toString(), requestVo.getNewPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            // 如果报错可能是因为刚好用户被禁用或删除
            subject.logout();
            throw new BusinessException(BusinessErrorEnum.UserNotLogin);
        }
    }

    public void deleteUser(Long userId) {
        Long currentUserId = (Long)SecurityUtils.getSubject().getPrincipal();
        if (userId.equals(currentUserId)) {
            throw new BusinessException(BusinessErrorEnum.CANT_DELETE_OWN_ACCOUNT);
        }
        User user = userService.getNotDeletedUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }
        // 删除该用户的所有会话
        SessionUtil.removeSessionsByUserId(userId, false);
        userService.deleteUserByUserId(userId);
    }

    public List<UserSearchResponseVo> getUsernameList(String searchName) {
        // TODO: 更换排序方式
        List<User> userList = userService.listUsersByName(searchName);
        List<UserSearchResponseVo> responseVoList = mapperFacade.mapAsList(userList, UserSearchResponseVo.class);
        return responseVoList;
    }

    public void updateBoardAdmin(Long userId, List<Long> boardIdList) {
        User user = userService.getNotDeletedUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }
        // 判断列表里的板块是否都存在
        Set<Long> notExistingBoardIdList = new HashSet<Long>(CollectionUtils.removeAll(boardIdList,
                boardService.listNotDeletedBoardIds()));
        log.debug("not exist {}", notExistingBoardIdList);
        if (!notExistingBoardIdList.isEmpty()) {
            throw new BusinessException(BusinessErrorEnum.BOARD_NOT_FOUND);
        }

        Set<Long> currentBoardIdList = boardAdminService.listBoardIdByUserId(userId);
        Set<Long> needToAdd = new HashSet<Long>(CollectionUtils.removeAll(boardIdList, currentBoardIdList));
        Set<Long> needToDelete = new HashSet<Long>(CollectionUtils.removeAll(currentBoardIdList, boardIdList));
        log.debug("old {}, after {}", currentBoardIdList, boardIdList);
        log.debug("need to add {}", needToAdd);
        log.debug("need to delete {}", needToDelete);
        boardAdminService.batchDeleteBoardAdminByUserId(userId, needToDelete);
        boardAdminService.batchAddBoardAdminByUserId(userId, needToAdd);
    }

    public void updateCategoryAdmin(Long userId, List<Long> categoryIdList) {
        User user = userService.getNotDeletedUserByUserId(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }
        // 判断列表里的分区是否都存在
        Set<Long> notExistingCategoryIdList = new HashSet<Long>(CollectionUtils.removeAll(categoryIdList,
                categoryService.listNotDeletedCategoryIds()));
        log.debug("not exist {}", notExistingCategoryIdList);
        if (!notExistingCategoryIdList.isEmpty()) {
            throw new BusinessException(BusinessErrorEnum.CATEGORY_NOT_FOUND);
        }

        Set<Long> currentCategoryIdList = categoryAdminService.listCategoryIdByUserId(userId);
        Set<Long> needToAdd = new HashSet<Long>(CollectionUtils.removeAll(categoryIdList, currentCategoryIdList));
        Set<Long> needToDelete = new HashSet<Long>(CollectionUtils.removeAll(currentCategoryIdList, categoryIdList));
        log.debug("old {}, after {}", currentCategoryIdList, categoryIdList);
        log.debug("need to add {}", needToAdd);
        log.debug("need to delete {}", needToDelete);
        categoryAdminService.batchDeleteCategoryAdminByUserId(userId, needToDelete);
        categoryAdminService.batchAddCategoryAdminByUserId(userId, needToAdd);
    }

    public void updateForumPermission(UpdateForumPermissionRequestVo requestVo) {
        User user = userService.getNotDeletedUserByUserId(requestVo.getUserId());
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }

        Permission permission = mapperFacade.map(requestVo, Permission.class);
        permissionService.addOrUpdateForumPermission(permission);
    }

    public void updateBoardPermission(UpdateBoardPermissionRequestVo requestVo) {
        User user = userService.getNotDeletedUserByUserId(requestVo.getUserId());
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }
        Board board = boardService.getNotDeletedBoardByBoardId(requestVo.getBoardId());
        if (board == null) {
            throw new BusinessException(BusinessErrorEnum.BOARD_NOT_FOUND);
        }

        Permission permission = mapperFacade.map(requestVo, Permission.class);
        permissionService.addOrUpdateBoardPermission(permission);
    }

    public void updateUserInfo(UpdateUserInfoRequestVo requestVo) {
        User user = userService.getNotDeletedUserByUserId(requestVo.getId());
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_FOUND);
        }
        User sameUsernameUser = userService.getNotDeletedUserByUsername(requestVo.getUsername());
        if (sameUsernameUser != null && !sameUsernameUser.getId().equals(user.getId())) {
            throw new BusinessException(BusinessErrorEnum.DUPLICATE_USERNAME);
        }

        boolean isRefreshSessionNeeded = !requestVo.getUsername().equals(user.getUsername()) ||
                !StringUtils.isEmpty(requestVo.getPassword());

        String plainPassword = requestVo.getPassword();

        if (!StringUtils.isEmpty(requestVo.getPassword())) {
            // 如果密码不为空的话就加密密码
            String encryptedPassword = PasswordUtil.encryptPasswordBcrypt(requestVo.getPassword());
            requestVo.setPassword(encryptedPassword);
        }

        mapperFacade.map(requestVo, user);
        userService.updateUser(user);

        if (isRefreshSessionNeeded) {
            // 删除该用户的会话
            SessionUtil.removeSessionsByUserId(user.getId(), true);

            // 如果修改的是自己的帐号
            Subject subject = SecurityUtils.getSubject();
            if (requestVo.getId().equals(subject.getPrincipal())) {
                // 用新的密码重新登录
                UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), plainPassword);

                try {
                    subject.login(token);
                } catch (AuthenticationException e) {
                    // 如果报错可能是因为刚好用户被禁用或删除
                    subject.logout();
                    throw new BusinessException(BusinessErrorEnum.UserNotLogin);
                }
            }
        }
    }

}
