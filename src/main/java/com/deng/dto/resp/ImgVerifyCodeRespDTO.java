package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :deng
 * @version :1.0
 * @description :交易Dto
 * @since :1.8
 */
@Data
@Builder
public class ImgVerifyCodeRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前会话ID，用于标识改图形验证码属于哪个会话
     */
    @Schema(description = "sessionId")
    private String sessionId;


    /**
     * Base64 编码的验证码图片
     */
    @Schema(description = "Base64 编码的验证码图片")
    private String img;
}
