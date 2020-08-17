package cn.wegfan.forum.service;

import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

public interface UserService extends IService<User> {

    User getNotDeletedUserByUserId(Long userId);

    User getNotDeletedUserByUsername(String username);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

    int addUserByRegister(User user);

}
