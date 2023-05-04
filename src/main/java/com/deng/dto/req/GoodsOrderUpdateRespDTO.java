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
public class GoodsOrderUpdateRespDTO {

    /**
     * 商品ID
     */
    @Schema(description = "订单ID", required = true)
    private Long orderId;


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
