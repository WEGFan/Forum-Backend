package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.request.UpdatePersonalPasswordRequestVo;
import cn.wegfan.forum.model.vo.request.UpdatePersonalUserInfoRequestVo;
import cn.wegfan.forum.model.vo.request.UserLoginRequestVo;
import cn.wegfan.forum.model.vo.request.UserRegisterRequestVo;
import cn.wegfan.forum.model.vo.response.UserLoginResponseVo;
import cn.wegfan.forum.model.vo.response.UserRoleResponseVo;
import cn.wegfan.forum.util.BusinessException;
import cn.wegfan.forum.util.PasswordUtil;
import cn.wegfan.forum.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        SessionUtil.removeOtherSessionsByUserId(userId);
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
        SessionUtil.removeOtherSessionsByUserId(userId);
        userService.deleteUserByUserId(userId);
    }

    public List<UserSearchResponseVo> getUsernameList(String searchName) {
        // TODO: 更换排序方式
        List<User> userList = userService.listUsersByName(searchName);
        List<UserSearchResponseVo> responseVoList = mapperFacade.mapAsList(userList, UserSearchResponseVo.class);
        return responseVoList;
    }

}
