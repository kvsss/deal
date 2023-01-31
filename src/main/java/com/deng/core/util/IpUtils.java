package com.deng.core.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :deng
 * @version :1.0
 * @description :Ip工具解析类
 * @since :1.8
 */
@UtilityClass
public class IpUtils {

    private static final String UNKNOWN_IP = "unknown";

    private static final String IP_SEPARATOR = ",";

    /**
     * 获取真实IP
     *
     * @return 真实IP
     */
    public String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(IP_SEPARATOR)) {
            String[] ipArray = ip.split(IP_SEPARATOR);
            ip = ipArray[0];
        }
        return ip;
    }
}

