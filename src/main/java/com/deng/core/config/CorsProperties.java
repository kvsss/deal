package com.deng.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description : 跨域属性
 * @since :1.8
 */
@ConfigurationProperties(prefix = "deal.cors")
@Data
public class CorsProperties {
    private List<String> allowOrigins;
}