package com.deng.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author :deng
 * @version :1.0
 * @description :商品添加DTO
 * @since :1.8
 */
@Data
public class GoodsOrderAddReqDTO {

    /**
     * 商品ID
     */
    @Schema(description = "商品ID", required = true)
    private Long goodsId;

    /**
     * 卖家ID
     */
    @Schema(description = "卖家ID", required = true)
    private Long sellerId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID", required = true)
    private Long buyerId;


    @Schema(description = "价格", required = true)
    private BigDecimal price;

    /**
     * 买家姓名
     */
    @Schema(description = "买家姓名", required = true)
    private String buyerName;

    /**
     * 买家地址
     */
    @Schema(description = "买家地址", required = true)
    private String buyerAddress;

    /**
     * 买家电话
     */
    @Schema(description = "买家电话", required = true)
    private String buyerPhone;

}
