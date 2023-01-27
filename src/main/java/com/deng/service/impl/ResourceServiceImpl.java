package com.deng.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.resp.ImgVerifyCodeRespDTO;
import com.deng.manage.redis.VerifyCodeManager;
import com.deng.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author :deng
 * @version :1.0
 * @description : 资源(图片,视屏)功能实现接口
 * @since :1.8
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Value("${deal.file.upload.path}")
    private String fileUploadPath;

    @Override
    public RestResp<ImgVerifyCodeRespDTO> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(ImgVerifyCodeRespDTO.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.genImgVerifyCode(sessionId))
                .build());
    }

    @Override
    public RestResp<String> uploadImage(MultipartFile file) {
        return null;
    }
}
