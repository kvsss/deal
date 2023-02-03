package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :商品分类
 * @since :1.8
 */
@Data
@Builder
public class GoodsCategoryRespDTO {

    /**
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long id;

    /**
     * 类别名
     */
    @Schema(description = "类别名")
    private String name;
}
