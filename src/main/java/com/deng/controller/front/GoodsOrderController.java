package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.service.GoodsOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 订单删除接口
     */
    @Operation(summary = "订单删除接口")
    @GetMapping("delete")
    public RestResp<Void> deleteOrder() {
        return goodsOrderService.deleteOrder();
    }

    /**
     * 订单修改接口
     */
    @Operation(summary = "订单修改接口")
    @GetMapping("update")
    public RestResp<Void> updateOrder() {
        return goodsOrderService.updateOrder();
    }


}
