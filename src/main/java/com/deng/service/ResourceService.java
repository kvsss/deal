package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.resp.ImgVerifyCodeRespDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface ResourceService {
    /**
     * 获得图片验证码
     * @return Base64编码的图片
     * @throws IOException 验证码图片生成失效
     */
    RestResp<ImgVerifyCodeRespDTO> getImgVerifyCode();

    /**
     * 上传图片
     * @param file
     * @return 生成图片的信息
     */
    RestResp<String> uploadImage(MultipartFile file);

}
