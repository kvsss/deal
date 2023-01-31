package com.deng.controller.front;

import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.dto.resp.ImgVerifyCodeRespDTO;
import com.deng.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Tag(name = "ResourceController", description = "前台门户-资源模块")
@RestController
@RequestMapping(FrontApiRouterConstants.FRONT_RESOURCE_API_URL_PREFIX)
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    /**
     * 获取图片验证码接口
     */
    @Operation(summary = "获取图片验证码接口")
    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDTO> getImgVerifyCode()  {
        return resourceService.getImgVerifyCode();
    }


    /**
     * 上传图片接口
     */
    @Operation(summary = "获取图片验证码接口")
    @PostMapping("uploadImage")
    public RestResp<String> uploadImage(
            @Parameter(description = "上传文件")
            @RequestParam("file") MultipartFile file)  {
        return resourceService.uploadImage(file);
    }
}
