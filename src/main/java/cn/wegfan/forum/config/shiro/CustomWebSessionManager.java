package cn.wegfan.forum.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

@Slf4j
public class CustomWebSessionManager extends DefaultWebSessionManager {

    /**
     * 获取session, 优化单次请求需要多次访问redis的问题
     * https://blog.csdn.net/qq_28988969/article/details/80080088
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        // 获取sessionId
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;
        // 在 Web 下使用 shiro 时这个 sessionKey 是 WebSessionKey 类型的
        // 若是在web下使用，则获取request
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }

        // 尝试从request中获取session
        if (request != null && sessionId != null) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (sessionObj != null) {
                log.info("从request获取到session：{}", sessionId);
                return (Session)sessionObj;
            }
        }

        // 若从request中获取session失败，则从redis中获取session，并把获取到的session存储到request中方便下次获取
        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null) {
            log.info("存储session到request中：{}", sessionId);
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }

}