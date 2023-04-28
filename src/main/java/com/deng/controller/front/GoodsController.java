package com.deng.controller.front;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
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
}
