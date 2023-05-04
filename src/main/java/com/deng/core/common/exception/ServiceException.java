package com.deng.core.common.exception;

import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author :deng
 * @version :1.0
 * @description :义务异常
 * @since :1.8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {
    private final CodeEnum codeEnum;

    public ServiceException(CodeEnum codeEnum) {
        super(codeEnum.getMessage(), null, false, false);
        this.codeEnum = codeEnum;
    }
}
