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
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(dto.getGoodsId());

        // 校验商品可购买
        if(goodsInfo.getGoodsStatus() != 0){
            return RestResp.fail(CodeEnum.USER_ORDER_FAIL);
        }

        // 修改商品的标记位为3:正在支付
        goodsInfo.setGoodsStatus(3);
        goodsInfoMapper.updateById(goodsInfo);

        // 修改缓存
        goodsInfoCacheManage.cachePutGoodsInfo(goodsInfo.getId());

        // 添加订单
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
}
