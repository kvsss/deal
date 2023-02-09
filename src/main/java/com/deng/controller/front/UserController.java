package com.deng.controller.front;

import com.deng.core.auth.UserHolder;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.*;
import com.deng.dto.resp.UserInfoRespDTO;
import com.deng.dto.resp.UserLoginRespDTO;
import com.deng.dto.resp.UserRegisterRespDTO;
import com.deng.service.GoodsService;
import com.deng.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    private final GoodsService goodsService;

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
        return goodsService.saveGoods(dto);
    }


    /**
     * 发表评论接口
     */
    @Operation(summary = "发表评论接口")
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDTO dto) {
        return goodsService.saveComment(dto);
    }


    /**
     * 修改评论接口
     */
    @Operation(summary = "修改评论接口")
    @PutMapping("comment/{id}/{uid}")
    public RestResp<Void> updateComment(@Parameter(description = "评论ID") @PathVariable Long id,
                                        @Parameter(description = "用户ID") @PathVariable Long uid,
                                        String content) {
        return goodsService.updateComment(uid, id, content);
    }

    /**
     * 删除评论接口
     */
    @Operation(summary = "删除评论接口")
    @DeleteMapping("comment/{id}/{uid}")
    public RestResp<Void> deleteComment(@Parameter(description = "评论ID") @PathVariable Long id,
                                        @Parameter(description = "用户ID") @PathVariable Long uid) {
        return goodsService.deleteComment(uid, id);
    }
}
