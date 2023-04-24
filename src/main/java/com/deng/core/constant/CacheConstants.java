package com.deng.core.constant;

import com.deng.core.time.TimeEnum;

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
     * Redis 缓存管理器
     */
    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    /**
     * caffeine 缓存管理器
     */
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     * 商品分类列表缓存
     */
    public static final String GOODS_CATEGORY_LIST_CACHE_NAME = "goodsCategoryListCache";


    /**
     * 商品点击榜缓存
     */
    public static final String GOODS_VISIT_RANK_CACHE_NAME = "goodsVisitRankCache";

    /**
     * 商品榜缓存
     */
    public static final String GOODS_NEWEST_RANK_CACHE_NAME = "goodsNewestRankCache";

    /**
     * 首页商品推荐缓存
     */
    public static final String HOME_GOODS_CACHE_NAME = "homeGoodsCache";


    /**
     * 商品信息查询
     */
    public static final String GOODS_INFO_CACHE_NAME = "goodsInfoCache";

    /**
     * redis存储验证码前缀
     */
    public static final String IMG_VERIFY_CODE_CACHE_KEY =
            REDIS_CACHE_PREFIX + "imgVerifyCodeCache::";


    /**
     * 缓存配置常量
     */
    public enum CacheEnum {
        /**
         * 商品类别缓存配置
         */
        GOODS_CATEGORY_LIST_CACHE(0, GOODS_CATEGORY_LIST_CACHE_NAME, TimeEnum.DAY_1.getTime(), 1),
        /**
         * 首页类别缓存配置
         */
        HOME_GOODS_CACHE(0, HOME_GOODS_CACHE_NAME, TimeEnum.DAY_1.getTime(), 1),
        /**
         * 商品点击缓存配置
         */
        GOODS_VISIT_RANK_CACHE(2, GOODS_VISIT_RANK_CACHE_NAME, TimeEnum.HOUR_1.getTime() * 6, 1),
        /**
         * 商品新出缓存配置
         */
        GOODS_NEWEST_RANK_CACHE(0, GOODS_NEWEST_RANK_CACHE_NAME, TimeEnum.MINUTE_1.getTime() * 30, 1),
        /**
         * 商品信息缓存配置
         */
        GOODS_INFO_CACHE(0, GOODS_INFO_CACHE_NAME, TimeEnum.HOUR_1.getTime() * 12, 500);


        /**
         * 0-本地，1-本地和远程，2-远程
         */
        private final int type;

        /**
         * 缓存的名字
         */
        private final String name;

        /**
         * 过期时间
         */
        private final int ttl;

        /**
         * 最大数量
         */
        private final int maxSize;


        CacheEnum(int type, String name, int ttl, int maxSize) {
            this.type = type;
            this.name = name;
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        /**
         * 是否为本地内存
         */
        public boolean isLocal() {
            return type <= 1;
        }

        /**
         * 是否为远程
         */
        public boolean isRemote() {
            return type >= 1;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }

        public int getMaxSize() {
            return maxSize;
        }
    }
}
