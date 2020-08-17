package cn.wegfan.forum.service;

import cn.wegfan.forum.constant.Constant;
import cn.wegfan.forum.constant.SexEnum;
import cn.wegfan.forum.mapper.UserMapper;
import cn.wegfan.forum.model.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    public User getNotDeletedUserByUserId(Long userId) {
        return userMapper.selectNotDeletedByUserId(userId);
    }

    @Override
    public User getNotDeletedUserByUsername(String username) {
        return userMapper.selectNotDeletedByUsername(username);
    }

    @Override
    public int updateUserLoginTimeAndIpByUserId(Long userId, Date loginTime, String loginIp) {
        return userMapper.updateUserLoginTimeAndIpByUserId(userId, loginTime, loginIp);
    }

    @Override
    public int addUserByRegister(User user) {
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

}
