package cn.wegfan.forum.mapper;

import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectNotDeletedByUserId(Long userId);

    User selectNotDeletedByUsername(String username);

    int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp);

}
