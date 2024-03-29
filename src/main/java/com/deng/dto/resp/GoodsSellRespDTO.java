package com.deng.dto.resp;

import com.deng.dto.resp.common.CommonOrderResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
@SuperBuilder
public class GoodsSellRespDTO extends CommonOrderResp {
    private static final long serialVersionUID = 1L;

}
