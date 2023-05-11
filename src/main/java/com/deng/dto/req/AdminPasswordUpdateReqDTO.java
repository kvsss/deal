package com.deng.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class AdminPasswordUpdateReqDTO {
    @Schema(description = "旧密码", required = true)
    String oldPassword;

    @Schema(description = "=新密码", required = true)
    String newPassword;
}
