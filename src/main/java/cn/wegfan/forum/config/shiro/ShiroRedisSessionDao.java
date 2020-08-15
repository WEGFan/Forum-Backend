package cn.wegfan.forum.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ShiroRedisSessionDao extends AbstractSessionDAO {

    @Resource(name = "shiroRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private final static String SESSION_PREFIX = "forum:session:";

    private String getKey(String key) {
        return SESSION_PREFIX + key;
    }

    @Override
    protected Serializable doCreate(Session session) {
        // 根据 session 生成 sessionId
        Serializable sessionId = generateSessionId(session);
        // 绑定 session 和对应的 sessionId
        assignSessionId(session, sessionId);
        String key = getKey(session.getId().toString());
        // 放入 redis 缓存
        redisTemplate.opsForValue().set(key, session);
        redisTemplate.expire(key, Duration.ofDays(1));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        String key = getKey(sessionId.toString());
        // 从缓存中根据 sessionId 获取对应 session
        Session session = (Session)redisTemplate.opsForValue().get(key);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        String key = getKey(session.getId().toString());
        // 根据传入 session 中的 sessionId 更新缓存中的 session
        redisTemplate.opsForValue().set(key, session);
        redisTemplate.expire(key, Duration.ofDays(1));
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        String key = getKey(session.getId().toString());
        // 根据传入 session 中的 sessionId 删除缓存中的 session
        redisTemplate.delete(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        // 获取缓存中所有的 session 的 key 的集合
        Set<String> keys = redisTemplate.keys(SESSION_PREFIX + "*");
        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }
        // 根据 key 合集遍历查找出对应 session
        for (String key : keys) {
            Session session = (Session)redisTemplate.opsForValue().get(key);
            sessions.add(session);
        }
        return sessions;
    }

}