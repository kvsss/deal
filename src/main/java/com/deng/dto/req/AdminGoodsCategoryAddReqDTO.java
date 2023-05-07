package com.deng.dto.req;

import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class AdminGoodsCategoryAddReqDTO {
    /**
     * 用户id
     */
    Long uid;

    /**
     * 分类名称
     */
    String name;
}
