package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.dao.entity.GoodsCategory;
import com.deng.dao.mapper.GoodsCategoryMapper;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import com.deng.service.GoodsCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    private final GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public List<GoodsCategoryRespDTO> listCategory() {
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        return goodsCategoryMapper.selectList(queryWrapper)
                .stream().map(v -> GoodsCategoryRespDTO.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .build()).collect(Collectors.toList());
    }
}
