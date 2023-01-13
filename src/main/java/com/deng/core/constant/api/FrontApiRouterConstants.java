package com.deng.core.constant.api;

import com.deng.core.constant.SystemConfigConstants;
import org.apiguardian.api.API;

/**
 * @author :deng
 * @version :1.0
 * @description : 商品接口门面信息API
 * @since :1.8
 */
public class FrontApiRouterConstants {
    private FrontApiRouterConstants() {
        throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
    }

    public static final String FRONT_URL_PREFIX = "/front";

    /**
     * 搜索模块请求路径前缀
     */
    public static final String SEARCH_URL_PREFIX = "/search";


    /**
     * 前台门户搜索相关API请求路径前缀
     */
    public static final String FRONT_SEARCH_API_URL_PREFIX = FRONT_URL_PREFIX + SEARCH_URL_PREFIX;
}
