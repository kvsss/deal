package com.deng.core.config;

import com.deng.core.constant.CacheConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :deng
 * @version :1.0
 * @description : 缓存配置类
 * @since :1.8
 */
@Configuration
public class CacheConfig {
    /**
     * caffeine 缓存配置
     * @return
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>(CacheConstants.CacheEnum.values().length);

        for (CacheConstants.CacheEnum value : CacheConstants.CacheEnum.values()) {
            if (value.isLocal()) {
                // 如果是本地内存
                Caffeine<Object, Object> caffeine = Caffeine.newBuilder().recordStats()
                        .maximumSize(value.getMaxSize());
                if (value.getTtl() > 0) {
                    // 如果有过期时间
                    caffeine.expireAfterWrite(Duration.ofSeconds(value.getTtl()));
                }
                caches.add(new CaffeineCache(value.getName(), caffeine.build()));
            }
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     *redis 配置
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(
                connectionFactory);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues().prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX);

        Map<String, RedisCacheConfiguration> cacheMap = new LinkedHashMap<>(
                CacheConstants.CacheEnum.values().length);

        for (CacheConstants.CacheEnum value : CacheConstants.CacheEnum.values()) {
            // 如果是远程的
            if (value.isRemote()) {
                if (value.getTtl() > 0) {
                    // 会过期的
                    cacheMap.put(value.getName(),
                            RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                                    .prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX)
                                    .entryTtl(Duration.ofSeconds(value.getTtl())));
                } else {
                    // 一直需要保存的
                    cacheMap.put(value.getName(),
                            RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                                    .prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX));
                }
            }
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,
                defaultCacheConfig, cacheMap);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.initializeCaches();
        return redisCacheManager;
    }
}
