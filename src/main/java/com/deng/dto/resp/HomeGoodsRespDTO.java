package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description : 首页响应商品数据DTO
 * @since :1.8
 */
@Data
public class HomeGoodsRespDTO implements Serializable {
    // 交给redis的类需要序列化
    private static final long serialVersionUID = 1L;

    /**
     * 类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
     */
    @Schema(description = "类型;-1-不起作用 0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐")
    private Integer type;

    /**
     * 推荐商品ID
     */
    @Schema(description = "商品ID")
    private Long goodsId;

    /**
     * 商品封面地址
     */
    @Schema(description = "商品封面地址")
    private String picUrl;

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
}
