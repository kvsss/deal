package com.deng.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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


}
