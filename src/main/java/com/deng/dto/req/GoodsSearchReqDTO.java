package com.deng.dto.req;

import com.deng.core.common.req.PageReqDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author :deng
 * @version :1.0
 * @description : 商品搜索 请求DTO
 * @since :1.8
 */
@Data
public class GoodsSearchReqDTO extends PageReqDTO {
    /**
     * 搜索关键字
     */
    @Parameter(description = "搜索关键字")
    private String keyword;

    /**
     * 商品类别
     */
    @Parameter(description = "分类ID")
    private Integer categoryId;

    /**
     * 上架时间
     * 如果使用Get请求，直接使用对象接收，则可以使用@DateTimeFormat注解进行格式化；
     * 如果使用Post请求，@RequestBody接收请求体参数，默认解析日期格式为yyyy-MM-dd HH:mm:ss ,
     * 如果需要接收其他格式的参数，则可以使用@JsonFormat注解
     */
    @Parameter(description = "最小更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTimeMin;


    /**
     * 排序字段
     * 上架时间或者点击量或者评论量
     */
    @Parameter(description = "排序字段")
    private String sort;


    /**
     * 场景0:不限制,1:个人,2:平台
     */
    @Parameter(description = "场景 0:不限制,1:个人,2:平台")
    private String scene;

}
