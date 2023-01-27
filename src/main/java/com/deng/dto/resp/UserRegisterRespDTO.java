package com.deng.dto.resp;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.UserRegisterReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :deng
 * @version :1.0
 * @description :用户注册返回Dto
 * @since :1.8
 */
@Data
@Builder
public class UserRegisterRespDTO {
    @Schema(description = "用户ID")
    private Long uid;

    @Schema(description = "用户token")
    private String token;

}
