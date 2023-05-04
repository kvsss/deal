package com.deng.controller.front;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */

import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.GoodsUpdateRespDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import com.deng.dto.resp.GoodsCommentRespDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.service.GoodsInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台门户-商品模块 API 控制器
 *
 * @author deng
 */
@Tag(name = "GoodsController", description = "前台门户-商品模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_GOODS_API_URL_PREFIX)
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsInfoService goodsInfoService;

    /**
     * 商品分类列表查询接口
     */
    @Operation(summary = "商品分类列表查询接口")
    @GetMapping("category/list")
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return goodsInfoService.listCategory();
    }


    /**
     * 商品信息查询接口
     */
    @Operation(summary = "商品信息查询接口")
    @GetMapping("{id}")
    public RestResp<GoodsInfoRespDTO> getGoodsById(
            @Parameter(description = "商品 ID") @PathVariable("id") Long goodsId) {
        return goodsInfoService.getGoodsById(goodsId);
    }


    /**
     * 增加商品点击量接口
     */
    @Operation(summary = "增加商品点击量接口")
    @PostMapping("visit")
    public RestResp<Void> addVisitCount(@Parameter(description = "商品ID") Long goodsId) {
        return goodsInfoService.addVisitCount(goodsId);
    }


    /**
     * 商品最新评论查询接口
     */
    @Operation(summary = "商品最新评论查询接口")
    @GetMapping("comment/newest_list")
    public RestResp<GoodsCommentRespDTO> listNewestComments(
            @Parameter(description = "商品ID") Long goodsId) {
        return goodsInfoService.listNewestComments(goodsId);
    }

    /**
     * 修改商品信息接口
     */
    @Operation(summary = "修改商品信息接口")
    @PutMapping("update")
    public RestResp<Void> updateGoods(@Parameter(description = "商品信息") @RequestBody GoodsUpdateRespDTO dto) {
        return goodsInfoService.updateGoods(dto);
    }

    /**
     * 删除商品信息接口
     */
    @Operation(summary = "删除商品信息接口")
    @DeleteMapping("delete/{id}")
    public RestResp<Void> deleteGoods(@Parameter(description = "商品ID") @PathVariable("id") Long goodsId) {
        return goodsInfoService.deleteGoods(goodsId);
    }

    /**
     * 下架商品
     */
    @Operation(summary = "下架商品")
    @PutMapping("offGoods/{id}")
    public RestResp<Void> offShelfGoods(@Parameter(description = "商品ID") @PathVariable("id") Long goodsId) {
        return goodsInfoService.offShelfGoods(goodsId);
    }

    /**
     * 上架商品
     */
    @Operation(summary = "上架商品")
    @PutMapping("onGoods/{id}")
    public RestResp<Void> onShelfGoods(@Parameter(description = "商品ID") @PathVariable("id") Long goodsId) {
        return goodsInfoService.onShelfGoods(goodsId);
    }


    // 同意商品上架
    @Operation(summary = "同意商品上架接口")
    @PutMapping("agreeOnGoods/{uid}/{goodsId}")
    public RestResp<Void> agreeApplyGoods(@Parameter(description = "用户ID") @PathVariable Long uid,
                                          @Parameter(description = "商品ID") @PathVariable Long goodsId) {
        return goodsInfoService.agreeApplyGoods(uid, goodsId);
    }

    // 不同意商品上架
    @Operation(summary = "不同意商品上架接口")
    @PutMapping("refuseOnGoods/{uid}/{goodsId}")
    public RestResp<Void> disagreeApplyGoods(@Parameter(description = "用户ID") @PathVariable Long uid,
                                             @Parameter(description = "商品ID") @PathVariable Long goodsId) {
        return goodsInfoService.disagreeApplyGoods(uid, goodsId);
    }

    // 平台下架商品
    @Operation(summary = "平台下架商品接口")
    @PutMapping("platformOffGoods/{uid}/{goodsId}")
    public RestResp<Void> platformOffGoods(@Parameter(description = "用户ID") @PathVariable Long uid,
                                           @Parameter(description = "商品ID") @PathVariable Long goodsId) {
        return goodsInfoService.platformOffGoods(uid, goodsId);
    }

}
