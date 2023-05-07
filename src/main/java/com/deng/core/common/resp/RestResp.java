package com.deng.core.common.resp;

import com.deng.core.common.constant.CodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Objects;

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

    /*
       public static RestResp<Void> fail(CodeEnum codeEnum) {
            return new RestResp<>(codeEnum);
        }
    */

    /**
     * 业务失败处理
     * 这里使用Object来处理通用返回
     *
     * @param codeEnum
     * @return
     */
    public static <T> RestResp<T> fail(CodeEnum codeEnum) {
        return new RestResp<>(codeEnum);
    }

    public static <T> RestResp<T> fail(String message) {
        RestResp<T> resp = new RestResp<>();
        resp.code = CodeEnum.FAIL.getCode();
        resp.message = message;
        resp.data = null;
        return resp;
    }


    /**
     * 系统错误
     */
    public static RestResp<Void> error() {
        return new RestResp<>(CodeEnum.SYSTEM_ERROR);
    }

    /**
     * 判断是否成功
     * 这个会在传输时，添加字段ok:true 或者 ok:false
     */
    public boolean isOk() {
        return Objects.equals(this.code, CodeEnum.OK.getCode());
    }

}
