package com.deng.dto.resp.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
@SuperBuilder
public class CommonGoodsResp {

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private Long goodsId;

    /**
     * 商品封面地址
     */
    @Schema(description = "商品封面地址")
    private String picUrl;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long uid;

    /**
     * 商品名
     */
    @Schema(description = "商品名")
    private String goodsTitle;

    /**
     * 商品描述
     */
    @Schema(description = "商品描述")
    private String goodsContent;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal price;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String nickName;

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

    /**
     * 商品状态(0:未售出,1:已售出,2:已下架)
     */
    @Schema(description = "商品状态(0:未售出,1:已售出,2:已下架)")
    private Integer goodsStatus;

    /**
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long categoryId;

    /**
     * 类别名
     */
    @Schema(description = "类别名")
    private String categoryName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 0:自己发布,1:平台发布
     */
    @Schema(description = "0:自己发布,1:平台发布")
    private String extra;
}
