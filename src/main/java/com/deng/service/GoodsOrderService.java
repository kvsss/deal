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
}
