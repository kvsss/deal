package com.deng.controller.front;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;
import com.deng.service.GoodsOrderService;
import com.deng.service.GoodsInfoService;
import com.deng.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author :deng
 * @version :1.0
 * @description :前台门户-会员模块 API 控制器
 * @since :1.8
 */
@Tag(name = "UserController", description = "前台门户-会员模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_USER_API_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final GoodsInfoService goodsInfoService;

    private final GoodsOrderService goodsOrderService;

    /**
     * 用户注册接口
     */
    @Operation(summary = "用户注册接口")
    @PostMapping("register")
    public RestResp<UserRegisterRespDTO> register(@Valid @RequestBody UserRegisterReqDTO dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录接口
     */
    @Operation(summary = "用户登录接口")
    @PostMapping("login")
    public RestResp<UserLoginRespDTO> login(@Valid @RequestBody UserLoginReqDTO dto) {
        return userService.login(dto);
    }

    /**
     * 用户删除接口
     */
    @Operation(summary = "用户删除接口")
    @DeleteMapping("deleteUser/{uid}")
    public RestResp<Void> deleteUser(@Parameter(description = "用户ID") @PathVariable Long uid) {
        return userService.deleteUser(uid);
    }


    /**
     * 获得用户信息
     */
    @Operation(summary = "用户信息查询接口")
    @GetMapping("userInfo/{uid}")
    public RestResp<UserInfoRespDTO> getUserInfo(
            @Parameter(description = "新闻ID") @PathVariable Long uid) {
        return userService.getUserInfo(uid);
    }

    /**
     * 修改用户信息
     */
    @Operation(summary = "用户信息修改接口")
    @PutMapping("updateUserInfo")
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUptReqDTO dto) {
        return userService.updateUserInfo(dto);
    }

    /**
     * 商品发布列表查询接口
     */
    @Operation(summary = "商品发布接口")
    @PostMapping("goods")
    public RestResp<Void> publishGoods(@Valid @RequestBody GoodsAddReqDTO dto) {
        return goodsInfoService.saveGoods(dto);
    }


    /**
     * 发表评论接口
     */
    @Operation(summary = "发表评论接口")
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDTO dto) {
        return goodsInfoService.saveComment(dto);
    }


    /**
     * 修改评论接口
     */
    @Operation(summary = "修改评论接口")
    @PutMapping("comment/{id}/{uid}")
    public RestResp<Void> updateComment(@Parameter(description = "评论ID") @PathVariable Long id,
                                        @Parameter(description = "用户ID") @PathVariable Long uid,
                                        String content) {
        return goodsInfoService.updateComment(uid, id, content);
    }

    /**
     * 删除评论接口
     */
    @Operation(summary = "删除评论接口")
    @DeleteMapping("comment/{id}/{uid}")
    public RestResp<Void> deleteComment(@Parameter(description = "评论ID") @PathVariable Long id,
                                        @Parameter(description = "用户ID") @PathVariable Long uid) {
        return goodsInfoService.deleteComment(uid, id);
    }

    /**
     * 用户购买商品接口
     */
    @Operation(summary = "用户购买商品接口")
    @PostMapping("goodsOrder")
    public RestResp<Void> buyGoods(@Valid @RequestBody GoodsOrderAddReqDTO dto) {
        return goodsOrderService.buyGoods(dto);
    }

    /**
     * 获取用户发布商品接口
     */
    @Operation(summary = "用户发布商品接口")
    @GetMapping("publicInfo")
    public RestResp<PageRespDTO<GoodsPublicRespDTO>> getPublicGoods(@ParameterObject GoodsPublicReqDTO condition) {
        return goodsInfoService.getPublicGoods(condition);
    }

    /**
     * 获取用户下架商品接口
     */
    @Operation(summary = "获得用户下架商品接口")
    @GetMapping("offInfo")
    public RestResp<PageRespDTO<GoodsOffRespDTO>> getOffGoods(@ParameterObject GoodsOffReqDTO condition) {
        return goodsInfoService.getOffGoods(condition);
    }


    @Operation(summary = "获得用户购买商品信息接口")
    @GetMapping("buyInfo")
    public RestResp<PageRespDTO<GoodsBuyRespDTO>> getBuyGoods(@ParameterObject GoodsBuyReqDTO condition) {
        return goodsOrderService.getBuyGoods(condition);
    }


    @Operation(summary = "获得用户卖出商品信息接口")
    @GetMapping("sellInfo")
    public RestResp<PageRespDTO<GoodsSellRespDTO>> getSellGoods(@ParameterObject GoodsSellReqDTO condition) {
        return goodsOrderService.getSellGoods(condition);
    }


    // 获得平台订单信息接口
    @Operation(summary = "获得平台订单信息接口")
    @GetMapping("platformOrder")
    public RestResp<PageRespDTO<GoodsPlatformOrderRespDTO>> getPlatformOrder(@ParameterObject GoodsPlatformOrderReqDTO condition) {
        return goodsOrderService.getPlatformOrder(condition);
    }


    @Operation(summary = "获得用户申请上架商品信息接口")
    @GetMapping("applyInfo")
    public RestResp<PageRespDTO<GoodsApplyRespDTO>> getApplyGoods(@ParameterObject GoodsApplyReqDTO condition) {
        return goodsInfoService.getApplyGoods(condition);
    }

    // 获得平台上架商品信息接口
    @Operation(summary = "获得平台上架商品信息接口")
    @GetMapping("goodsInfo")
    public RestResp<PageRespDTO<GoodsPlatformRespDTO>> getPlatformGoods(@ParameterObject GoodsPlatformReqDTO condition) {
        return goodsInfoService.getPlatformGoods(condition);
    }

    // 获得商家卖家信息
    @Operation(summary = "获得商家卖家信息")
    @GetMapping("seller/{goodsId}")
    public RestResp<SellerRespDTO> getSeller(@PathVariable  Long goodsId){
        return goodsOrderService.getSeller(goodsId);
    }
}
