package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoRespDTO {
    /**
     * ID
     */
    @Schema(description = "商品ID")
    private Long id;

/*    *//**
     * 类别ID
     *//*
    @Schema(description = "类别ID")
    private Long categoryId;*/

/*    *//**
     * 类别名
     *//*
    @Schema(description = "类别名")
    private String categoryName;*/

    /**
     * 商品名
     */
    @Schema(description = "类别名")
    private String goodsName;

}
