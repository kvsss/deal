package com.deng.core.constant;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */

/**
 * @author :deng
 * @version :1.0
 * @description :系统常量
 * @since :1.8
 */
public final class SystemConfigConstants {

    private SystemConfigConstants() {
        throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * Http 请求认证 Header
     */
    public static final String HTTP_AUTH_HEADER_NAME = "Authorization";

    /**
     * 常量类实例化异常信息
     */
    public static final String CONST_INSTANCE_EXCEPTION_MSG = "instantiate Constant class";

    /**
     * 前台门户标识
     */
    public static final String DEAL_FRONT_KEY = "front";
}
