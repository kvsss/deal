package com.deng.dto.es;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deng.dao.entity.GoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description : es 数据返回
 * @since :1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsGoodsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 类别名
     */
    private String categoryName;

    /**
     * 商品封面地址
     */
    private String picUrl;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品描述
     */
    private String goodsContent;

    /**
     * 点击量
     */
    private Long visitCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public static EsGoodsDTO build(GoodsInfo goodsInfo) {
        // 构造
        return EsGoodsDTO.builder()
                .id(goodsInfo.getId())
                .categoryId(goodsInfo.getCategoryId())
                .categoryName(goodsInfo.getCategoryName())
                .picUrl(goodsInfo.getPicUrl())
                .uid(goodsInfo.getUid())
                .nickName(goodsInfo.getNickName())
                .goodsPrice(goodsInfo.getGoodsPrice())
                .goodsTitle(goodsInfo.getGoodsTitle())
                .goodsContent(goodsInfo.getGoodsContent())
                .visitCount(goodsInfo.getVisitCount())
                .commentCount(goodsInfo.getCommentCount())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
}
