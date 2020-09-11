package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.constant.UserTypeEnum;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface UserService extends IService<User> {

    long countNotDeletedUsers();

    User getUserByUserId(Long userId);

    User getCurrentLoginUser();

    User getNotDeletedUserByUserId(Long userId);

    User getNotDeletedUserByUsername(String username);

    User getNotDeletedUserByEmail(String email);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

    int addUserByRegister(User user);

    int addUserByAdmin(User user);

    int updateUserPersonalInfoByUserId(Long userId, String nickname, SexEnum sex, String signature);

    int updateUserPasswordByUserId(Long userId, String newPassword);

    int deleteUserByUserId(Long userId);

    List<User> listUsersByName(String user);

    int updateUser(User user);

    Page<User> listNotDeletedUsersByPageAndUsernameAndType(Page<?> page, Long userId, UserTypeEnum userType, String username);

    List<User> listNotDeletedCategoryAdminsByCategoryId(Long categoryId);

    List<User> listNotDeletedBoardAdminsByBoardId(Long boardId);

    int updateUserAvatarByUserId(Long userId, String avatarPath);

    int increaseUserTopicCountByUserId(Long userId);

    int increaseUserReplyCountByUserId(Long userId);

    int updateUserEmailVerifiedByUserId(Long userId);

    int updateUserEmailByUserId(Long userId, String email);

}
