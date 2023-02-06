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
        List<HomeGoods> result = homeGoodsMapper.selectList(queryWrapper);

        if (!CollectionUtils.isEmpty(result)) {
            List <Long> goodsIds = result.stream().map(HomeGoods::getGoodsId).collect((Collectors.toList()));
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
                    return homeGoodsRespDTO;
                }).collect(Collectors.toList());
            }
        }
        // 否者返回空的集合
        return Collections.emptyList();
    }
}
