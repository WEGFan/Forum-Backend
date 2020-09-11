package cn.wegfan.forum.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class IpUtil {

    private static HttpServletRequest httpServletRequest;

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        IpUtil.httpServletRequest = httpServletRequest;
    }

    public static String getIpAddress() {
        String ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            return "";
        }
        return ipAddress;
    }

}