package com.deng.manage.cache;

import com.deng.core.constant.CacheConstants;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dto.resp.GoodsInfoRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author :deng
 * @version :1.0
 * @description :商品信息缓存
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
public class GoodsInfoCacheManage {
    private final GoodsInfoMapper goodsInfoMapper;


    /** 商品信息查询
     * @param goodsId
     * @return
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,
            value = CacheConstants.GOODS_INFO_CACHE_NAME)
    public GoodsInfoRespDTO getGoodsById(Long id) {
        return cachePutGoodsInfo(id);
    }


    /**
     * 缓存商品信息（不管缓存中是否存在都执行方法体中的逻辑，然后缓存起来）
     */
    @CachePut(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,
            value = CacheConstants.GOODS_INFO_CACHE_NAME)
    public GoodsInfoRespDTO cachePutGoodsInfo(Long id) {
        // 查询基础信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(id);
        return GoodsInfoRespDTO.builder().
                goodsId(goodsInfo.getId())
                .price(goodsInfo.getGoodsPrice())
                .goodsTitle(goodsInfo.getGoodsTitle())
                .picUrl(goodsInfo.getPicUrl())
                .goodsContent(goodsInfo.getGoodsContent())
                .nickName(goodsInfo.getNickName())
                .build();
    }
}
