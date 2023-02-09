package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :deng
 * @version :1.0
 * @description :用户信息返回
 * @since :1.8
 */
@Data
@Builder
public class UserInfoRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "昵称")
    private String nickName;

    /**
     * 用户头像
     * */
    @Schema(description = "用户头像")
    private String userPhoto;

    /**
     * 用户性别
     * */
    @Schema(description = "用户性别")
    private Integer userSex;
}
