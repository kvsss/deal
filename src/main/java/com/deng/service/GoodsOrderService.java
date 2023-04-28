package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.GoodsOrderAddReqDTO;

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
     * 订单修改接口
     */
    RestResp<Void> updateOrder();

}
