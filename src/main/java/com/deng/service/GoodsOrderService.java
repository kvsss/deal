package com.deng.service;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.*;
import com.deng.dto.resp.GoodsBuyRespDTO;
import com.deng.dto.resp.GoodsPlatformOrderRespDTO;
import com.deng.dto.resp.GoodsSellRespDTO;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface GoodsOrderService {
    /**
     * 购买商品
     * @param dto
     * @return
     */
    RestResp<Void> buyGoods(GoodsOrderAddReqDTO dto);
/*
    *//**
     * 订单列表查询接口
     * @return
     *//*
    RestResp<List<GoodsOrderRespDTO>> listOrder();*/

    /**
     * 订单删除接口
     */
    RestResp<Void> deleteOrder();


    /**
     * 订单查询接口
     */
    RestResp<PageRespDTO<GoodsBuyRespDTO>> getBuyGoods(GoodsBuyReqDTO condition);

    /**
     * 订单查询接口
     */
    RestResp<PageRespDTO<GoodsSellRespDTO>> getSellGoods(GoodsSellReqDTO condition);

    /**
     * 平台订单查询接口
     * @param condition
     * @return
     */
    RestResp<PageRespDTO<GoodsPlatformOrderRespDTO>> getPlatformOrder(GoodsPlatformOrderReqDTO condition);


    /**
     * 取消交易接口
     * @param orderId
     * @return
     */
    RestResp<Void> cancelOrder(Long orderId);

    /**
     * 完成交易接口
     * @param orderId
     * @return
     */
    RestResp<Void> finishOrder(Long orderId);

    /**
     * 订单修改接口
     * @param dto
     * @return
     */
    RestResp<Void> updateOrder(GoodsOrderUpdateRespDTO dto);

}
