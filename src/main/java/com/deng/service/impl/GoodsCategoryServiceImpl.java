package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsCategory;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.mapper.GoodsCategoryMapper;
import com.deng.dto.AdminGoodsCategoryUpdateReqDTO;
import com.deng.dto.req.AdminGoodsCategoryAddReqDTO;
import com.deng.dto.req.AdminGoodsCategoryReqDTO;
import com.deng.dto.resp.AdminGoodsCategoryRespDTO;
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


    @Override
    public RestResp<PageRespDTO<AdminGoodsCategoryRespDTO>> getAllGoodsCategory(AdminGoodsCategoryReqDTO condition) {
        Page<GoodsCategory> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());

        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();

        if (condition.getKeyword() == null || condition.getKeyword().isEmpty()) {
            queryWrapper = null;
        } else {
            queryWrapper.like(DateBaseConstants.GoodsCategoryTable.COLUMN_NAME, condition.getKeyword());
        }

        IPage<GoodsCategory> result = goodsCategoryMapper.selectPage(page, queryWrapper);
        List<GoodsCategory> records = result.getRecords();

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                records.stream().map(
                        goodsCategory -> AdminGoodsCategoryRespDTO.builder()
                                .id(goodsCategory.getId())
                                .name(goodsCategory.getName())
                                .build()
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp addGoodsCategory(AdminGoodsCategoryAddReqDTO condition) {
        // 查询是否存在同名的商品分类
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCategoryTable.COLUMN_NAME, condition.getName());
        GoodsCategory goodsCategory = goodsCategoryMapper.selectOne(queryWrapper);
        if (goodsCategory != null) {
            return RestResp.fail("已存在同名的商品分类");
        }
        GoodsCategory category = new GoodsCategory();
        category.setName(condition.getName());
        goodsCategoryMapper.insert(category);
        return RestResp.ok("添加成功");
    }

    @Override
    public RestResp updateGoodsCategory(AdminGoodsCategoryUpdateReqDTO condition) {
        // 查询是否存在同名的商品分类
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCategoryTable.COLUMN_NAME, condition.getName());
        GoodsCategory goodsCategory = goodsCategoryMapper.selectOne(queryWrapper);
        if (goodsCategory != null) {
            return RestResp.fail("已存在同名的商品分类");
        }

        GoodsCategory category = new GoodsCategory();
        category.setId(condition.getId());
        category.setName(condition.getName());
        goodsCategoryMapper.updateById(category);
        return RestResp.ok("修改成功");
    }

    @Override
    public RestResp deleteGoodsCategory(Long uid, Long categoryId) {
        //查看商品中是否有该分类
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), categoryId);
        GoodsCategory goodsCategory = goodsCategoryMapper.selectOne(queryWrapper);
        if (goodsCategory == null) {
            return RestResp.fail("该分类不存在");
        }
        goodsCategoryMapper.deleteById(categoryId);
        return RestResp.ok("删除成功");
    }
}
