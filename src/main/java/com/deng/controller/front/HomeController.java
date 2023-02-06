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
     * 首页小说推荐查询接口
     */
    @Operation(summary = "首页商品推荐查询接口")
    @GetMapping("goods")
    public RestResp<List<HomeGoodsRespDTO>> listHomeBooks() {
        return homeService.listHomeGoods();
    }
}
