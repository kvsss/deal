package com.deng.dto.resp.common;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CommonOrderResp {

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

    ////////////////////////////////////////////////////////////////

    @Schema(description = "订单主键")
    private Long orderId;

    @Schema(description = "卖家id")
    private Long sellerId;

    @Schema(description = "买家id")
    private Long buyerId;

    @Schema(description = "买家名称")
    private String buyerName;

    @Schema(description = "买家地址")
    private String buyerAddress;

    @Schema(description = "买家电话")
    private String buyerPhone;

    @Schema(description = "卖家电话")
    private String sellerPhone;

    @Schema(description = "订单状态:0未完成 1已完成 2已取消")
    private Integer status;

    @Schema(description = "下单时间")
    private LocalDateTime createTime;

    @Schema(description = "最后更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "完成时间,在已完成时设置")
    private LocalDateTime completeTime;

    /**
     * 0:自己发布,1:平台发布
     */
    @Schema(description = "0:自己发布,1:平台发布")
    private String extra;
}
