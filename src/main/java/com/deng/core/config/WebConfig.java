package com.deng.core.config;

import com.deng.core.constant.SystemConfigConstants;
import com.deng.core.interceptor.AuthInterceptor;
import com.deng.core.interceptor.FileInterceptor;
import com.deng.core.interceptor.FlowLimitInterceptor;
import com.deng.core.interceptor.TokenParseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author :deng
 * @version :1.0
 * @description : web 配置,这里主要是加各种拦截器
 * @since :1.8
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FlowLimitInterceptor flowLimitInterceptor;

    private final AuthInterceptor authInterceptor;

    private final FileInterceptor fileInterceptor;

    private final TokenParseInterceptor tokenParseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 流量限制拦截器
        registry.addInterceptor(flowLimitInterceptor)
                .addPathPatterns("/**")
                .order(0);

        // 文件访问拦截
        registry.addInterceptor(fileInterceptor)
                .addPathPatterns(SystemConfigConstants.IMAGE_UPLOAD_DIRECTORY + "**")
                .order(1);


    }
}
