package cn.wegfan.forum.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.util.Collection;

/**
 * 会话工具类
 */
public class SessionUtil {

    /**
     * 根据用户编号删除当前会话以外的所有会话
     *
     * @param userId 用户编号
     */
    public static void removeOtherSessionsByUserId(Long userId) {
        // TODO: 优化 直接使用hashset存取
        if (userId == null) {
            return;
        }

        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        SessionDAO sessionDao = sessionManager.getSessionDAO();
        Collection<Session> sessions = sessionDao.getActiveSessions();

        String currentSessionId = (String)SecurityUtils.getSubject().getSession().getId();
        for (Session session : sessions) {
            String sessionUserId = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            String sessionId = (String)session.getId();
            if (sessionUserId.equals(userId.toString()) && !sessionId.equals(currentSessionId)) {
                sessionDao.delete(session);
            }
        }
    }

}
