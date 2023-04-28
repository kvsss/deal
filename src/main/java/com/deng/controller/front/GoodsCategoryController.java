package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Tag(name = "GoodsCategoryController", description = "前台门户-商品类别模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_CATEGORY_API_URL_PREFIX)
@RequiredArgsConstructor
public class GoodsCategoryController {

/*
    *//**
     * 商品分类列表查询接口
     *//*
    @Operation(summary = "商品分类列表查询接口")
    @GetMapping("category/list")
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return goodsService.listCategory();
    }


    *//**
     * 商品分类删除接口
     *//*
    @Operation(summary = "商品分类删除接口")
    @GetMapping("category/delete")
    public RestResp<Void> deleteCategory() {
        return goodsService.deleteCategory();
    }

    *//**
     * 商品分类修改接口
     *//*
    @Operation(summary = "商品分类修改接口")
    @GetMapping("category/update")
    public RestResp<Void> updateCategory() {
        return goodsService.updateCategory();
    }*/
}
