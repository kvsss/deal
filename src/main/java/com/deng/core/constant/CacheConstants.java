package com.deng.core.constant;

/**
 * @author :deng
 * @version :1.0
 * @description : 缓存
 * @since :1.8
 */
public final class CacheConstants {

    private CacheConstants() {
        throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * redis 前缀
     */
    public static final String REDIS_CACHE_PREFIX = "Cache::Deal::";


    /**
     * redis存储验证码前缀
     */
    public static final String IMG_VERIFY_CODE_CACHE_KEY =
            REDIS_CACHE_PREFIX + "imgVerifyCodeCache::";
}
