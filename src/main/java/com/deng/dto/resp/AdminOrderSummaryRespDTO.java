package com.deng.dto.resp;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class AdminOrderSummaryRespDTO {


    @Schema(description = "标题")
    private String[] titles;

    @Schema(description = "订单总数列表")
    private Long[] orderCounts;

    @Schema(description = "订单总金额列表")
    private Double[] orderAmounts;
}
