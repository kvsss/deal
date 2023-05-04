package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dto.resp.HomeGoodsRespDTO;
import com.deng.manage.cache.HomeGoodsCacheManager;
import com.deng.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :deng
 * @version :1.0
 * @description :门户功能实现页面
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final HomeGoodsCacheManager homeGoodsCacheManager;

    private final GoodsInfoMapper goodsInfoMapper;


    @Override
    public RestResp<List<HomeGoodsRespDTO>> listHomeGoods() {
        return RestResp.ok(homeGoodsCacheManager.listHomeGoods());
    }

    @Override
    public RestResp<List<HomeGoodsRespDTO>> listVisitRankGoods() {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        // 访问量排序
        goodsInfoQueryWrapper
                .eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 0)
                .orderByDesc(DateBaseConstants.GoodsInfoTable.COLUMN_VISIT_COUNT)
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_12.getLimitSql());

        return RestResp.ok(listRankGoods(goodsInfoQueryWrapper));
    }

    @Override
    public RestResp<List<HomeGoodsRespDTO>> listNewestRankGoods() {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        // 创建时间排序
        goodsInfoQueryWrapper
                .eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 0)
                .orderByDesc(DateBaseConstants.CommonColumnEnum.CREATE_TIME.getName())
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_12.getLimitSql());

        return RestResp.ok(listRankGoods(goodsInfoQueryWrapper));
    }

    @Override
    public RestResp<List<HomeGoodsRespDTO>> listPlatformGoods() {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        // 创建时间排序
        goodsInfoQueryWrapper
                .eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 0)
                .eq(DateBaseConstants.GoodsInfoTable.COLUMN_EXTRA, "1")
                .orderByDesc(DateBaseConstants.CommonColumnEnum.CREATE_TIME.getName())
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_18.getLimitSql());

        return RestResp.ok(listRankGoods(goodsInfoQueryWrapper));
    }

    /**
     * 查询排行榜商品
     *
     * @param goodsInfoQueryWrapper
     * @return
     */
    private List<HomeGoodsRespDTO> listRankGoods(QueryWrapper<GoodsInfo> goodsInfoQueryWrapper) {
        return goodsInfoMapper.selectList(goodsInfoQueryWrapper).stream().map(goodsInfo -> {
            HomeGoodsRespDTO homeGoodsRespDTO = new HomeGoodsRespDTO();
            // type 不起作用
            homeGoodsRespDTO.setType(-1);
            homeGoodsRespDTO.setGoodsId(goodsInfo.getId());
            homeGoodsRespDTO.setPicUrl(goodsInfo.getPicUrl());
            homeGoodsRespDTO.setGoodsTitle(goodsInfo.getGoodsTitle());
            homeGoodsRespDTO.setGoodsContent(goodsInfo.getGoodsContent());
            homeGoodsRespDTO.setPrice(goodsInfo.getGoodsPrice());
            homeGoodsRespDTO.setNickName(goodsInfo.getNickName());
            homeGoodsRespDTO.setGoodsStatus(goodsInfo.getGoodsStatus());
            homeGoodsRespDTO.setOldDegree(goodsInfo.getOldDegree());
            homeGoodsRespDTO.setBuyTime(goodsInfo.getBuyTime());
            homeGoodsRespDTO.setExtra(goodsInfo.getExtra());
            return homeGoodsRespDTO;
        }).collect(Collectors.toList());
    }


}
