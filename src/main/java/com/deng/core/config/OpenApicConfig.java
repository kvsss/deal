package com.deng.core.config;

import com.deng.core.constant.SystemConfigConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author :deng
 * @version :1.0
 * @description :swagger文档生成配置
 * @since :1.8
 */
@Configuration
@Profile("dev") // dev环境
@OpenAPIDefinition(info = @Info(title = "novel 项目接口文档", version = "v3.2.0", license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
@SecurityScheme(type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, name = SystemConfigConstants.HTTP_AUTH_HEADER_NAME, description = "登录 token")
public class OpenApicConfig {
}
