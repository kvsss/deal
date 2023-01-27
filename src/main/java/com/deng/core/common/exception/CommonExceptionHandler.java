package com.deng.core.common.exception;

import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author :deng
 * @version :1.0
 * @description : 统一异常处理
 * @since :1.8
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
/*    @ExceptionHandler(ServiceException.class)
    public RestResp<Void> handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return null;
    }*/


/*    @ExceptionHandler(IllegalStateException.class)
    public RestResp<Void> handleIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage(), e);
        return RestResp.error(CodeEnum.SYSTEM_ERROR);
    }*/

    /**
     * 处理系统异常,这里会全局捕获
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResp<Void> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResp.error();
    }

}
