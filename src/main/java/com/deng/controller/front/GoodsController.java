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
import com.deng.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final GoodsService goodsService;

    /**
     * 商品分类列表查询接口
     */
    @Operation(summary = "商品分类列表查询接口")
    @GetMapping("category/list")
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return goodsService.listCategory();
    }
}
