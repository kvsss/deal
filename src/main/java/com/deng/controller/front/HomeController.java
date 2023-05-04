package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.resp.HomeGoodsRespDTO;
import com.deng.service.HomeService;
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
@Tag(name = "HomeController", description = "前台门户-首页模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_HOME_API_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /**
     * 首页商品推荐查询接口，这里默认查询了热门推荐和精品推荐
     */
    @Operation(summary = "首页商品推荐查询接口")
    @GetMapping("goods")
    public RestResp<List<HomeGoodsRespDTO>> listHomeGoods() {
        return homeService.listHomeGoods();
    }

    /**
     * 商品点击榜查询接口
     */
    @Operation(summary = "商品点击榜查询接口")
    @GetMapping("visit_rank")
    public RestResp<List<HomeGoodsRespDTO>> listVisitRankGoods() {
        return homeService.listVisitRankGoods();
    }


    /**
     * 商品榜查询接口
     */
    @Operation(summary = "商品新发布查询接口")
    @GetMapping("newest_rank")
    public RestResp<List<HomeGoodsRespDTO>> listNewestRankGoods() {
        return homeService.listNewestRankGoods();
    }

    /**
     * 列出平台发布商品
     */
    @Operation(summary = "平台发布商品查询接口")
    @GetMapping("platform_goods")
    public RestResp<List<HomeGoodsRespDTO>> listPlatformGoods() {
        return homeService.listPlatformGoods();
    }
}
