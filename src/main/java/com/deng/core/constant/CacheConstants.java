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


    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     *  商品分类列表缓存
     */
    public static final String GOODS_CATEGORY_LIST_CACHE_NAME = "goodsCategoryListCache";

    /**
     * 首页商品推荐缓存
     */
    public static final String HOME_GOODS_CACHE_NAME = "homeGoodsCache";

    /**
     * redis存储验证码前缀
     */
    public static final String IMG_VERIFY_CODE_CACHE_KEY =
            REDIS_CACHE_PREFIX + "imgVerifyCodeCache::";
}
