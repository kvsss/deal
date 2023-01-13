package com.deng.controller.front;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :deng
 * @version :1.0
 * @description :前台门户-会员模块 API 控制器
 * @since :1.8
 */
@Tag(name = "UserController", description = "前台门户-会员模块")
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
}
