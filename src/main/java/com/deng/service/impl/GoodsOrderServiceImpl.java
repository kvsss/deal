package com.deng.service.impl;

import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.GoodsOrder;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dao.mapper.GoodsOrderMapper;
import com.deng.dto.req.GoodsOrderAddReqDTO;
import com.deng.manage.cache.GoodsInfoCacheManage;
import com.deng.service.GoodsOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsOrderServiceImpl implements GoodsOrderService {
    private final GoodsOrderMapper goodsOrderMapper;

    private final GoodsInfoCacheManage goodsInfoCacheManage;

    private final GoodsInfoMapper goodsInfoMapper;

    @Override
    public RestResp<Void> buyGoods(GoodsOrderAddReqDTO dto) {
        // 行级索防止超卖
        if (goodsInfoMapper.updateGoodsStatus(dto.getGoodsId()) == 0) {
            return RestResp.fail(CodeEnum.USER_ORDER_FAIL);
        }

        // 添加商品订单信息
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsId(dto.getGoodsId());
        goodsOrder.setSellerId(dto.getSellerId());
        goodsOrder.setBuyerId(dto.getBuyerId());
        goodsOrder.setBuyerAddress(dto.getBuyerAddress());
        goodsOrder.setBuyerName(dto.getBuyerName());
        goodsOrder.setBuyerPhone(dto.getBuyerPhone());
        goodsOrder.setCreateTime(LocalDateTime.now());
        goodsOrder.setUpdateTime(LocalDateTime.now());
        goodsOrder.setPrice(dto.getPrice());
        goodsOrder.setStatus(0);
        goodsOrderMapper.insert(goodsOrder);
        return RestResp.ok();
    }

/*    @Override
    public RestResp<List<GoodsOrderRespDTO>> listOrder() {
        return null;
    }*/

    @Override
    public RestResp<Void> deleteOrder() {
        return null;
    }

    @Override
    public RestResp<Void> updateOrder() {
        return null;
    }
}
