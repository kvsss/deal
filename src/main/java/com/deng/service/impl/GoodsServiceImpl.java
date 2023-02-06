package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.deng.core.common.resp.RestResp;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dto.req.GoodsAddReqDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import com.deng.manage.cache.GoodsCategoryCacheManage;
import com.deng.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    private final GoodsCategoryCacheManage goodsCategoryCacheManage;

    private final GoodsInfoMapper goodsInfoMapper;


    @Override
    public RestResp<Void> saveGoods(GoodsAddReqDTO dto) {
        // 校验商品名是否已存在


        GoodsInfo goodsInfo = new GoodsInfo();
        // 填充数据
        goodsInfo.setCategoryId(dto.getCategoryId());
        goodsInfo.setCategoryName(dto.getCategoryName());
        goodsInfo.setPicUrl(dto.getPicUrl());
        goodsInfo.setUid(dto.getUid());
        goodsInfo.setNickName(dto.getNickName());

        goodsInfo.setGoodsPrice(dto.getPrice());
        goodsInfo.setGoodsTitle(dto.getGoodsTitle());
        goodsInfo.setGoodsContent(dto.getGoodsContent());
        goodsInfo.setCreateTime(LocalDateTime.now());
        goodsInfo.setUpdateTime(LocalDateTime.now());

        goodsInfoMapper.insert(goodsInfo);

        return RestResp.ok();
    }

    @Override
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return RestResp.ok(goodsCategoryCacheManage.listCategory());
    }
}
