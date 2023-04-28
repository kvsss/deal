package com.deng.dto.req;

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
public class GoodsBuyReqDTO extends PageReqDTO {
    /**
     * 用户id
     */
    private Long uid;

    /**
     * 搜索关键字
     */
    @Parameter(description = "搜索关键字")
    private String keyword;

}
