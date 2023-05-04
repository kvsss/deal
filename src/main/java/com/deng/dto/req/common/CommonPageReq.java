package com.deng.dto.req.common;

import com.deng.core.common.req.PageReqDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class CommonPageReq extends PageReqDTO {

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 搜索关键字
     */
    @Parameter(description = "搜索关键字")
    private String keyword;

    /**
     * 额外的字段,可以用来做其他的查询,以实现其他的查询功能
     */
    @Parameter(description = "搜索关键字")
    private  String extra;
}
