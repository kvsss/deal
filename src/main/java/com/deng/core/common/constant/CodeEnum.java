package com.deng.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {
    /**
     * 正常
     */
    OK("00000", "一切正常"),

    /**
     * 用户验证码错误
     */
    USER_VERIFY_CODE_ERROR("A0001", "用户验证码错误"),

    /**
     * 用户已存在
     */
    USER_NAME_EXIST("A0002", "用户已经存在"),

    /**
     * 用户已存在
     */
    USER_NOT_EXIST("A0003","用户不存在"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR("A0004","用户密码错误"),




    /**
     * 用户登录已过期
     */
    USER_LOGIN_EXPIRED("A0230", "用户登录已过期"),


    /**
     * 系统异常
     */
    SYSTEM_ERROR("Z0001", "系统错误");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 中文描述
     */
    private final String message;

}
