package com.deng.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品推荐
 * </p>
 *
 * @author ${author}
 * @since 2023/02/06
 */
@TableName("home_goods")
public class HomeGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 推荐类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
     */
    private Integer type;

    /**
     * 推荐排序
     */
    private Integer sort;

    /**
     * 推荐商品ID
     */
    private Long goodsId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    @Override
    public String toString() {
        return "HomeGoods{" +
        "id=" + id +
        ", type=" + type +
        ", sort=" + sort +
        ", goodsId=" + goodsId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
