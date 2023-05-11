package com.deng.dto.req;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class AdminOrderSummaryReqDTO {
    @Parameter(description = "搜索关键字")
    private String extra;
}
