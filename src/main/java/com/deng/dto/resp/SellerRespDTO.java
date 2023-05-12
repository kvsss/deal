package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
@Builder
public class SellerRespDTO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "昵称")
    private String nickName;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userPhoto;


    /**
     * 发布数量
     */
    @Schema(description = "发布数量")
    private Long publicCount;


    /**
     * 成交数量
     */
    @Schema(description = "成交数量")
    private Long makeCount;
}
