package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
@SuperBuilder
public class AdminUserRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long uid;

    @Schema(description = "账号")
    private String userName;

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

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "用户角色")
    private String role;
}
