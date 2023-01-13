package com.deng.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@TableName("goods_info")
@Getter
public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名
     */
    private String goodsName;



}
