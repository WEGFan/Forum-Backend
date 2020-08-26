package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface UserService extends IService<User> {

    User getNotDeletedUserByUserId(Long userId);

    User getNotDeletedUserByUsername(String username);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

    int addUserByRegister(User user);

    int updateUserPersonalInfoByUserId(Long userId, String nickname, SexEnum sex, String signature);

    int updateUserPasswordByUserId(Long userId, String newPassword);

    int deleteUserByUserId(Long userId);

    List<User> listUsersByName(String user);

    int updateUser(User user);

}
