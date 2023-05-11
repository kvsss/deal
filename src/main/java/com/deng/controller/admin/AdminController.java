package com.deng.controller.admin;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.AdminGoodsCategoryUpdateReqDTO;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;
import com.deng.service.GoodsCategoryService;
import com.deng.service.GoodsInfoService;
import com.deng.service.GoodsOrderService;
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
 * @description :
 * @since :1.8
 */
@Tag(name = "AdminController", description = "后台门户-管理员模块")
@RestController
@RequestMapping(FrontApiRouterConstants.BEHIND_ADMIN_API_URL_PREFIX)
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final GoodsCategoryService goodsCategoryService;

    private final GoodsInfoService goodsInfoService;

    private final GoodsOrderService goodsOrderService;


    // 管理员登录
/*    @Operation(summary = "管理员登录")
    @GetMapping("login")
    public RestResp<AdminLoginRespDTO> login(@ParameterObject AdminLoginReqDTO condition) {
        return userService.login(condition);
    }*/


    // 获得用户所有信息
    @Operation(summary = "获得用户所有信息")
    @GetMapping("userInfo")
    public RestResp<PageRespDTO<AdminUserRespDTO>> getAllUserInfo(@ParameterObject AdminUserInfoReqDTO condition) {
        return userService.getAllUserInfo(condition);
    }

    // 商品类型管理
    @Operation(summary = "商品类型管理")
    @GetMapping("categoryInfo")
    public RestResp<PageRespDTO<AdminGoodsCategoryRespDTO>> getAllGoodsCategory(@ParameterObject AdminGoodsCategoryReqDTO condition) {
        return goodsCategoryService.getAllGoodsCategory(condition);
    }

    // 商品管理
    @Operation(summary = "商品管理")
    @GetMapping("goodsInfo")
    public RestResp<PageRespDTO<AdminGoodsRespDTO>> getAllGoods(@ParameterObject AdminGoodsReqDTO condition) {
        return goodsInfoService.getAllGoods(condition);
    }


    // 商品订单管理
    @Operation(summary = "商品订单管理")
    @GetMapping("orderInfo")
    public RestResp<PageRespDTO<AdminGoodsOrderRespDTO>> getAllGoodsOrder(@ParameterObject AdminGoodsOrderReqDTO condition) {
        return goodsOrderService.getAllGoodsOrder(condition);
    }


    // 添加商品类型
    @Operation(summary = "添加商品类型")
    @PostMapping("category")
    public RestResp addGoodsCategory(@RequestBody AdminGoodsCategoryAddReqDTO condition) {
        return goodsCategoryService.addGoodsCategory(condition);
    }

    // 修改商品类型
    @Operation(summary = "修改商品类型")
    @PutMapping("category")
    public RestResp updateGoodsCategory(@RequestBody AdminGoodsCategoryUpdateReqDTO condition) {
        return goodsCategoryService.updateGoodsCategory(condition);
    }

    // 删除商品类型
    @Operation(summary = "删除商品类型")
    @DeleteMapping("category/{uid}/{categoryId}")
    public RestResp deleteGoodsCategory(
            @Parameter(description = "商品类型id") @PathVariable Long categoryId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return goodsCategoryService.deleteGoodsCategory(uid, categoryId);
    }

    // 管理员下架商品
    @Operation(summary = "管理员下架商品")
    @PutMapping("goods/{uid}/{goodsId}")
    public RestResp adminOffGoods(
            @Parameter(description = "商品id") @PathVariable Long goodsId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return goodsInfoService.adminOffGoods(uid, goodsId);
    }


    /**
     * 管理员删除商品信息接口
     */
    @Operation(summary = "删除商品信息接口")
    @DeleteMapping("goods/{uid}/{goodsId}")
    public RestResp<Void> adminDeleteGoods(
            @Parameter(description = "商品id") @PathVariable Long goodsId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return goodsInfoService.adminDeleteGoods(uid, goodsId);
    }

    //管理员禁用用户接口
    @Operation(summary = "管理员禁用用户接口")
    @PutMapping("user/{uid}/{userId}")
    public RestResp<Void> adminDisableUser(
            @Parameter(description = "目标用户id") @PathVariable Long userId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return userService.adminDisableUser(uid, userId);
    }

    //管理员开启平台账号接口
    @Operation(summary = "管理员开启平台账号接口")
    @PutMapping("platform/{uid}/{userId}")
    public RestResp<Void> adminOpenUser(
            @Parameter(description = "目标用户id") @PathVariable Long userId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return userService.adminOpenUser(uid, userId);
    }

    // 删除用户接口
    @Operation(summary = "删除用户接口")
    @DeleteMapping("user/{uid}/{userId}")
    public RestResp<Void> adminDeleteUser(
            @Parameter(description = "目标用户id") @PathVariable Long userId,
            @Parameter(description = "用户ID") @PathVariable Long uid) {
        return userService.adminDeleteUser(uid, userId);
    }


    /**
     * 用户登录接口
     */
    @Operation(summary = "用户登录接口")
    @PostMapping("login")
    public RestResp<UserLoginRespDTO> login(@Valid @RequestBody UserLoginReqDTO dto) {
        return userService.adminLogin(dto);
    }


    // 管理员修改密码
    @Operation(summary = "管理员修改密码")
    @PutMapping("password")
    public RestResp<Void> updateAdminPassword(@RequestBody AdminPasswordUpdateReqDTO condition) {
        return userService.updateAdminPassword(condition);
    }


    // 管理员修改个人信息
    @Operation(summary = "管理员修改个人信息")
    @PutMapping("info")
    public RestResp<Void> updateAdminInfo(@RequestBody AdminInfoUpdateReqDTO condition) {
        return userService.updateAdminInfo(condition);
    }

    //获取订单的汇总
    @Operation(summary = "获取订单的汇总")
    @GetMapping("orderSummary")
    public RestResp<AdminOrderSummaryRespDTO> getOrderSummary(@ParameterObject AdminOrderSummaryReqDTO condition) {
        return goodsOrderService.getOrderSummary(condition);
    }

}
