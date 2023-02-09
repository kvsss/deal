package com.deng.manage.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.constant.CacheConstants;
import com.deng.dao.entity.GoodsCategory;
import com.deng.dao.mapper.GoodsCategoryMapper;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :deng
 * @version :1.0
 * @description :商品分类缓存管理
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
public class GoodsCategoryCacheManage {
    private final GoodsCategoryMapper goodsCategoryMapper;

    /**
     * 将分类放入缓存
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,
            value = CacheConstants.GOODS_CATEGORY_LIST_CACHE_NAME)
    public List<GoodsCategoryRespDTO> listCategory() {
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        return goodsCategoryMapper.selectList(queryWrapper)
                .stream().map(v -> GoodsCategoryRespDTO.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .build()).collect(Collectors.toList());
    }


}
