package com.deng.core.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author :deng
 * @version :1.0
 * @description :spring security配置
 * @since :1.8
 */
@Configuration
public class WebSecurityConfig {
    // 加入后第一次访问就不需要进行认证了
    // 来看看这个的作用
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 不开启，csrf 我们使用jwt去实现
                .requestMatcher(EndpointRequest.toAnyEndpoint()) // 只对站点进行验证
                .authorizeRequests(requests -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"));// 验证身份
        http.httpBasic();
        return http.build();
    }
}
