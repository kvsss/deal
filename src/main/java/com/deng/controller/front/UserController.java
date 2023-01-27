package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.req.UserLoginReqDTO;
import com.deng.dto.req.UserRegisterReqDTO;
import com.deng.dto.resp.UserLoginRespDTO;
import com.deng.dto.resp.UserRegisterRespDTO;
import com.deng.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
