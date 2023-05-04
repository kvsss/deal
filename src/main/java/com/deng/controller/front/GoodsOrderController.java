package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.GoodsOrderUpdateRespDTO;
import com.deng.dto.req.GoodsUpdateRespDTO;
import com.deng.service.GoodsOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Tag(name = "GoodsOrderController", description = "前台门户-订单模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_ORDER_API_URL_PREFIX)
@RequiredArgsConstructor
public class GoodsOrderController {
    private final GoodsOrderService goodsOrderService;

    /*    *//**
     * 订单列表查询接口
     *//*
    @Operation(summary = "订单列表查询接口")
    @GetMapping("list")
    public RestResp<List<GoodsOrderRespDTO>> listOrder() {
        return goodsOrderService.listOrder();
    }*/


    @Operation(summary = "取消交易接口")
    @PutMapping("cancel/{id}")
    public RestResp<Void> cancelOrder(@Parameter(description = "订单ID") @PathVariable("id") Long orderId) {
        return goodsOrderService.cancelOrder(orderId);
    }

    /**
     * 完成交易接口
     */
    @Operation(summary = "完成交易接口")
    @PutMapping("finish/{id}")
    public RestResp<Void> finishOrder(@Parameter(description = "订单ID") @PathVariable("id") Long orderId) {
        return goodsOrderService.finishOrder(orderId);
    }

    /**
     * 修改订单信息接口
     */
    @Operation(summary = "修改订单信息接口")
    @PutMapping("update")
    public RestResp<Void> updateOrder(@Parameter(description = "商品信息") @RequestBody GoodsOrderUpdateRespDTO dto) {
        return goodsOrderService.updateOrder(dto);
    }

}
