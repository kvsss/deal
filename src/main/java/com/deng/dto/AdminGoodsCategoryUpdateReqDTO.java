package com.deng.dto;

import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class AdminGoodsCategoryUpdateReqDTO {
    /**
     * 用户id
     */
    Long uid;


    /**
     * 分类id
     */
    Long id;

    /**
     * 分类名称
     */
    String name;
}
