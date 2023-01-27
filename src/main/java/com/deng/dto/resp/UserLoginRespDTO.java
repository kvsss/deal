package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :用户登录请求Dto
 * @since :1.8
 */
@Data
@Builder
public class UserLoginRespDTO {

    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "用户名")
    private String nickName;

    @Schema(description = "用户token")
    private String token;
}
