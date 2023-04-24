package com.deng.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author :deng
 * @version :1.0
 * @description :用户评论请求dto
 * @since :1.8
 */
@Data
public class UserCommentReqDTO {
    private Long userId;

    @Schema(description = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空！")
    private Long goodsId;

    @Schema(description = "评论内容", required = true)
    @NotBlank(message = "评论不能为空！")
    @Length(min = 1, max = 512)
    private String commentContent;
}
