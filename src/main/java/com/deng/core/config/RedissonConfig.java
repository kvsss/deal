package com.deng.core.config;

import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :deng
 * @version :1.0
 * @description :Redisson配置类
 * @since :1.8
 */
@Configuration
public class RedissonConfig {
    @Bean
    @SneakyThrows
    public RedissonClient redissonClient() {
        Config config = Config.fromYAML(getClass().getResource("/redisson.yml"));
        return Redisson.create(config);
    }
}
