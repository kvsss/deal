package com.deng.core.common.req;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description : 分页求请求数据封装，所有分页请求Dto都应继承该类
 * @since :1.8
 */
@Data
public class PageReqDTO {
    /**
     * 请求页码,默认第1页
     */
    @Parameter(description = "请求页码，默认第 1 页")
    private int pageNum = 1;

    /**
     * 每页的大小
     */
    @Parameter(description = "每页大小，默认每页 10 条")
    private int pageSize = 10;

    /**
     * 是否查询所有,默认不查询所有 为true时,pageNum 和 pageSize 无效
     */
/*    @Parameter(description = "是否查询所有,默认为false,为true时,pageNum 和 pageSize 无效")
    private boolean fetchAll = false;*/
}
