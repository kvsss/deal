package com.deng.dto.req;

import com.deng.core.common.req.PageReqDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;

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
}
