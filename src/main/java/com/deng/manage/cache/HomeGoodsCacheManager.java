package com.deng.manage.cache;

import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.constant.CacheConstants;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.HomeGoods;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dao.mapper.HomeGoodsMapper;
import com.deng.dto.resp.HomeGoodsRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author :deng
 * @version :1.0
 * @description :home门面推荐信息缓存
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
public class HomeGoodsCacheManager {
    private final HomeGoodsMapper homeGoodsMapper;

    private final GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询首页商品推荐，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,
            value = CacheConstants.HOME_GOODS_CACHE_NAME)
    public List<HomeGoodsRespDTO> listHomeGoods() {
        QueryWrapper<HomeGoods> queryWrapper = new QueryWrapper<>();
        // 优先级排序
        queryWrapper.orderByAsc(DateBaseConstants.CommonColumnEnum.SORT.getName());
        queryWrapper.last(DateBaseConstants.LimitSQLtEnum.LIMIT_30.getLimitSql());
        List<HomeGoods> result = homeGoodsMapper.selectList(queryWrapper);

        if (!CollectionUtils.isEmpty(result)) {
            List<Long> goodsIds = result.stream().map(HomeGoods::getGoodsId).collect((Collectors.toList()));
            QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
            goodsInfoQueryWrapper.in(DateBaseConstants.CommonColumnEnum.ID.getName(), goodsIds);
            // 查询具体的商品信息
            List<GoodsInfo> goodsInfos = goodsInfoMapper.selectList(goodsInfoQueryWrapper);

            // 转为map
            Map<Long, GoodsInfo> goodsInfoMap =
                    goodsInfos.stream().collect(Collectors.toMap(GoodsInfo::getId, Function.identity()));

            if (!CollectionUtils.isEmpty(goodsInfos)) {
                // HomeGoods + GoodsInfo 转换为HomeGoodsRespDTO
                return result.stream().map(homeGoods -> {
                    HomeGoodsRespDTO homeGoodsRespDTO = new HomeGoodsRespDTO();
                    GoodsInfo goodsInfo = goodsInfoMap.get(homeGoods.getGoodsId());
                    homeGoodsRespDTO.setType(homeGoods.getType());
                    homeGoodsRespDTO.setGoodsId(goodsInfo.getId());
                    homeGoodsRespDTO.setPicUrl(goodsInfo.getPicUrl());
                    homeGoodsRespDTO.setGoodsTitle(goodsInfo.getGoodsTitle());
                    homeGoodsRespDTO.setGoodsContent(goodsInfo.getGoodsContent());
                    homeGoodsRespDTO.setPrice(goodsInfo.getGoodsPrice());
                    homeGoodsRespDTO.setNickName(goodsInfo.getNickName());
                    homeGoodsRespDTO.setGoodsStatus(goodsInfo.getGoodsStatus());
                    homeGoodsRespDTO.setOldDegree(goodsInfo.getOldDegree());
                    homeGoodsRespDTO.setBuyTime(goodsInfo.getBuyTime());
                    return homeGoodsRespDTO;
                }).collect(Collectors.toList());
            }
        }
        // 否者返回空的集合
        return Collections.emptyList();
    }


    /**
     * 查询商品点击榜列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER,
            value = CacheConstants.GOODS_VISIT_RANK_CACHE_NAME)
    public List<HomeGoodsRespDTO> listVisitRankGoods() {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        // 访问量排序
        goodsInfoQueryWrapper.orderByDesc(DateBaseConstants.GoodsInfoTable.COLUMN_VISIT_COUNT);
        return listRankGoods(goodsInfoQueryWrapper);
    }


    /**
     * 查询商品榜列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,
            value = CacheConstants.GOODS_NEWEST_RANK_CACHE_NAME)
    public List<HomeGoodsRespDTO> listNewestRankGoods() {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        // 创建时间排序
        goodsInfoQueryWrapper
                .orderByDesc(DateBaseConstants.CommonColumnEnum.CREATE_TIME.getName());
        return listRankGoods(goodsInfoQueryWrapper);
    }

    private List<HomeGoodsRespDTO> listRankGoods(QueryWrapper<GoodsInfo> goodsInfoQueryWrapper) {
        goodsInfoQueryWrapper
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_30.getLimitSql());
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
            return homeGoodsRespDTO;
        }).collect(Collectors.toList());
    }
}
