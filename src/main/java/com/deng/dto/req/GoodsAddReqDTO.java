package com.deng.dto.req;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class GoodsAddReqDTO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", required = true)
    @NotNull
    private Long uid;

    @Schema(description = "用户名", required = true)
    @NotNull
    private String nickName;

    /**
     * 类别ID
     */
    @Schema(description = "类别ID", required = true)
    @NotNull
    private Long categoryId;

    /**
     * 类别名
     */
    @Schema(description = "类别名", required = true)
    @NotBlank
    private String categoryName;

    /**
     * 价格
     */
    @Schema(description = "价格", required = true)
    private BigDecimal price;

    /**
     * 商品封面地址
     */
    @Schema(description = "商品封面地址", required = true)
    @NotBlank
    private String picUrl;

    /**
     * 商品
     */
    @Schema(description = "商品标题", required = true)
    @NotBlank
    private String goodsTitle;

    /**
     * 商品详细
     */
    @Schema(description = "商品详细", required = true)
    @NotBlank
    private String goodsContent;

    /**
     * 购买时间
     */
    @Schema(description = "购买时间")
    private LocalDateTime buyTime;

    /**
     * 新旧程度(1-10成新)
     */
    @Schema(description = "新旧程度(1-10成新)")
    private Integer oldDegree;

    @Schema(description = "额外的字段,(用数值时，起作用)")
    private String extra;
}
