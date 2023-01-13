package com.deng.controller.front;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author :deng
 * @version :1.0
 * @description : 前台门户-搜索API控制器
 * @since :1.8
 */
@Tag(name = "SearchController", description = "前台门户-搜索模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_SEARCH_API_URL_PREFIX)
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "商品搜索接口")
    @GetMapping("goods")
    public RestResp<PageRespDTO<GoodsInfoRespDTO>> searchGoods(
            @ParameterObject GoodsSearchReqDTO condition) {
        return searchService.search(condition);
    }

}
