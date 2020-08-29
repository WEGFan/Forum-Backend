package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.constant.UserTypeEnum;
import cn.wegfan.forum.mapper.UserMapper;
import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.util.EscapeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "user")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public User getCurrentLoginUser() {
        Long userId = (Long)SecurityUtils.getSubject().getPrincipal();
        return userMapper.selectNotDeletedByUserId(userId);
    }

    @Override
    public User getNotDeletedUserByUserId(Long userId) {
        return userMapper.selectNotDeletedByUserId(userId);
    }

    @Override
    public User getNotDeletedUserByUsername(String username) {
        return userMapper.selectNotDeletedByUsername(username);
    }

    @Override
    public User getNotDeletedUserByEmail(String email) {
        return userMapper.selectNotDeletedByEmail(email);
    }

    @Override
    public int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp) {
        return userMapper.updateUserLoginTimeAndIpByUserId(userId, loginTime, loginIp);
    }

    @Override
    public int addUserByRegister(User user) {
        // TODO: 换一种删空格方式
        user.setNickname(StringUtils.deleteWhitespace(user.getNickname()));
        user.setEmailVerified(false);
        user.setSex(SexEnum.SECRET);
        user.setSignature("");
        user.setSuperBoardAdmin(false);
        user.setAdmin(false);
        user.setAvatarPath(Constant.DEFAULT_AVATAR_PATH);
        user.setTopicCount(0L);
        user.setReplyCount(0L);
        Date now = new Date();
        user.setRegisterTime(now);
        user.setUpdateTime(now);
        return userMapper.insert(user);
    }

    @Override
    public int addUserByAdmin(User user) {
        // TODO: 换一种删空格方式
        user.setNickname(StringUtils.deleteWhitespace(user.getNickname()));
        user.setEmailVerified(false);
        user.setSex(SexEnum.SECRET);
        user.setSignature("");
        user.setSuperBoardAdmin(false);
        user.setAvatarPath(Constant.DEFAULT_AVATAR_PATH);
        user.setTopicCount(0L);
        user.setReplyCount(0L);
        Date now = new Date();
        user.setRegisterTime(now);
        user.setUpdateTime(now);
        return userMapper.insert(user);
    }

    @Override
    public int updateUserPersonalInfoByUserId(Long userId, String nickname, SexEnum sex, String signature) {
        return userMapper.updateUserPersonalInfoByUserId(userId, nickname, sex, signature);
    }

    @Override
    public int updateUserPasswordByUserId(Long userId, String newPassword) {
        return userMapper.updateUserPasswordByUserId(userId, newPassword);
    }

    @Override
    public int deleteUserByUserId(Long userId) {
        return userMapper.deleteByUserId(userId);
    }

    @Override
    public List<User> listUsersByName(String user) {
        user = EscapeUtil.escapeSqlLike(user);
        return userMapper.selectListByName(user);
    }

    @Override
    public int updateUser(User user) {
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    @Override
    public Page<User> listNotDeletedUsersByPageAndUsernameAndType(Page<?> page, UserTypeEnum userType, String username) {
        if (StringUtils.isEmpty(username)) {
            username = null;
        } else {
            username = username.trim();
            username = EscapeUtil.escapeSqlLike(username);
        }

        switch (userType) {
            case NORMAL_USER:
                return userMapper.selectNotDeletedNormalUserListByPageAndUsername(page, username);
            case BOARD_ADMIN:
                return userMapper.selectNotDeletedBoardAdminListByPageAndUsername(page, username);
            case CATEGORY_ADMIN:
                return userMapper.selectNotDeletedCategoryAdminListByPageAndUsername(page, username);
            case SUPER_BOARD_ADMIN:
                return userMapper.selectNotDeletedSuperBoardAdminListByPageAndUsername(page, username);
            case ADMIN:
                return userMapper.selectNotDeletedAdminListByPageAndUsername(page, username);
            case BAN_VISIT:
                return userMapper.selectNotDeletedBanVisitListByPageAndUsername(page, username);
            case BAN_REPLY:
                return userMapper.selectNotDeletedBanCreateTopicAndReplyListByPageAndUsername(page, username);
            case ALL:
            default:
                return userMapper.selectNotDeletedUserListByPageAndUsername(page, username);
        }
    }

    @Override
    public List<User> listNotDeletedCategoryAdminsByCategoryId(Long categoryId) {
        return userMapper.selectNotDeletedCategoryAdminListByCategoryId(categoryId);
    }

    @Override
    public List<User> listNotDeletedBoardAdminsByBoardId(Long boardId) {
        return userMapper.selectNotDeletedBoardAdminListByBoardId(boardId);
    }

    @Override
    public int updateUserAvatarByUserId(Long userId, String avatarPath) {
        return userMapper.updateUserAvatarByUserId(userId, avatarPath);
    }

}
