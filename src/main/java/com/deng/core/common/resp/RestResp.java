package com.deng.core.common.resp;

import com.deng.core.common.constant.CodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author :deng
 * @version :1.0
 * @description : Http Rest响应数据封装
 * @since :1.8
 */
@Getter
public class RestResp<T> {
    /**
     * 响应码
     */
    @Schema(description = "错误码，00000-没有错误")
    private String code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    private RestResp() {
        this.code = CodeEnum.OK.getCode();
        this.message = CodeEnum.OK.getMessage();
    }

    private RestResp(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    private RestResp(T data) {
        this();
        this.data = data;
    }

    /**
     * 数据处理成功，无返回数据
     */
    public static RestResp<Void> ok() {
        return new RestResp<>();
    }

    /**
     * 数据处理成功，有返回数据
     */
    public static <T> RestResp<T> ok(T data) {
        return new RestResp<>(data);
    }

    /**
     * 业务处理失败
     */
    public static RestResp<Void> fail(CodeEnum codeEnum) {
        return new RestResp<>(codeEnum);
    }

    /**
     * 系统错误
     */
    public static RestResp<Void> error(CodeEnum codeEnum) {
        return new RestResp<>(codeEnum);
    }

}
