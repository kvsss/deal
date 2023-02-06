package com.deng.core.constant.api;

import com.deng.core.constant.SystemConfigConstants;
import org.apiguardian.api.API;

/**
 * @author :deng
 * @version :1.0
 * @description : 商品接口门面信息API
 * @since :1.8
 */
public final class FrontApiRouterConstants {
    private FrontApiRouterConstants() {
        throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
    }

    public static final String FRONT_URL_PREFIX = "/front";

    /**
     * 搜索模块请求路径前缀
     */
    public static final String SEARCH_URL_PREFIX = "/search";

    /**
     * 资源请求前缀
     */
    public static final String RESOURCE_URL_PREFIX = "/resource";

    /**
     * 登录请求前缀
     */
    public static final String USER_URL_PREFIX = "/user";

    /**
     * 商品请求求前缀
     */
    public static final String GOODS_URL_PREFIX = "/goods";

    /**
     *门户请求前缀
     */
    public static final String HOME_URL_PREFIX = "/home";


    /**
     * 前台门户搜索相关API请求路径前缀
     */
    public static final String FRONT_SEARCH_API_URL_PREFIX = FRONT_URL_PREFIX + SEARCH_URL_PREFIX;

    /**
     * 前台资源请求前缀
     */
    public static final String FRONT_RESOURCE_API_URL_PREFIX = FRONT_URL_PREFIX + RESOURCE_URL_PREFIX;

    /**
     * 前台用户请求前缀
     */
    public static final String FRONT_USER_API_URL_PREFIX = FRONT_URL_PREFIX + USER_URL_PREFIX;

    /**
     * 前台商品请求前缀
     */
    public static final String FRONT_GOODS_API_URL_PREFIX = FRONT_URL_PREFIX + GOODS_URL_PREFIX;

    /**
     *   前台门户请求前缀
     */
    public static final String FRONT_HOME_API_URL_PREFIX = FRONT_URL_PREFIX + HOME_URL_PREFIX;



}
