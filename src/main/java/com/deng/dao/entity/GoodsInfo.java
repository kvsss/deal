package com.deng.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author ${author}
 * @since 2023/05/01
 */
@TableName("goods_info")
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 0:未售出,1:已售出,2:已下架,3:交易中
     */
    private Integer goodsStatus;

    /**
     * 购买时间
     */
    private LocalDateTime buyTime;

    /**
     * 新旧程度 1-10
     */
    private Integer oldDegree;

    /**
     * 0:自己发布,1:平台发布
     */
    private String extra;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public LocalDateTime getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(LocalDateTime buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getOldDegree() {
        return oldDegree;
    }

    public void setOldDegree(Integer oldDegree) {
        this.oldDegree = oldDegree;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", categoryName=" + categoryName +
        ", picUrl=" + picUrl +
        ", uid=" + uid +
        ", nickName=" + nickName +
        ", goodsPrice=" + goodsPrice +
        ", goodsTitle=" + goodsTitle +
        ", goodsContent=" + goodsContent +
        ", visitCount=" + visitCount +
        ", commentCount=" + commentCount +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", goodsStatus=" + goodsStatus +
        ", buyTime=" + buyTime +
        ", oldDegree=" + oldDegree +
        ", extra=" + extra +
        "}";
    }
}
