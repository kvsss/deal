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
public class AdminInfoUpdateReqDTO {
    @Schema(description = "名称", required = true)
    String nickName;


}
