package com.deng.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.SystemConfigConstants;
import com.deng.dto.resp.ImgVerifyCodeRespDTO;
import com.deng.manage.redis.VerifyCodeManager;
import com.deng.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
    public RestResp<ImgVerifyCodeRespDTO> getImgVerifyCode() {
        String sessionId = IdWorker.get32UUID();
        String img = null;
        try {
            img = verifyCodeManager.genImgVerifyCode(sessionId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResp.fail(CodeEnum.IMG_VERIFY_CODE_FAIL);
        }
        return RestResp.ok(ImgVerifyCodeRespDTO.builder()
                .sessionId(sessionId)
                .img(img)
                .build());
    }

    @Override
    public RestResp<String> uploadImage(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        String savePath =
                SystemConfigConstants.IMAGE_UPLOAD_DIRECTORY
                        + now.format(DateTimeFormatter.ofPattern("yyyy")) + File.separator
                        + now.format(DateTimeFormatter.ofPattern("MM")) + File.separator
                        + now.format(DateTimeFormatter.ofPattern("dd"));
        String oriName = file.getOriginalFilename();
        assert oriName != null;
        String saveFileName = IdWorker.get32UUID() + oriName.substring(oriName.lastIndexOf("."));
        File saveFile = new File(fileUploadPath + savePath, saveFileName);

        // 创建文件夹
        // 这里逐层创建
        if (!saveFile.getParentFile().exists()) {
            boolean isSuccess = saveFile.getParentFile().mkdirs();
            if (!isSuccess) {
                // 创建失败
                return RestResp.fail(CodeEnum.USER_UPLOAD_FILE_ERROR);
            }
        }
        try {
            file.transferTo(saveFile);
            if (Objects.isNull(ImageIO.read(saveFile))) {
                // 上传的文件不是图片
                Files.delete(saveFile.toPath());
                return RestResp.fail(CodeEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            // 上传失败
            return RestResp.fail(CodeEnum.USER_UPLOAD_FILE_ERROR);
        }

        // 返回文件名
        return RestResp.ok(savePath + File.separator + saveFileName);
    }
}
