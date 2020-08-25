package cn.wegfan.forum.mapper;

import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectNotDeletedByUserId(Long userId);

    User selectNotDeletedByUsername(String username);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

    int updateUserPersonalInfoByUserId(Long userId, String nickname, SexEnum sex, String signature);

    int updateUserPasswordByUserId(Long userId, String newPassword);

    int deleteByUserId(Long userId);

    List<User> selectListByName(String user);

}
