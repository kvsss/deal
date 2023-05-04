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
    USER_NOT_EXIST("A0003", "用户不存在"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR("A0004", "用户密码错误"),

    /**
     * 请求限制
     */
    USER_REQUEST_LIMIT("A0005", "请求超出限制"),

    /**
     * 用户上传文件异常
     */
    USER_UPLOAD_FILE_ERROR("A0006", "用户上传文件异常"),

    /**
     * 文件类型不匹配
     */
    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A0007", "上传文件类型不匹配"),

    /**
     * 验证码生成失败
     */
    IMG_VERIFY_CODE_FAIL("A0008", "验证码生成失败"),

    USER_COMMENTED("A0009", "用户已评论"),

    USER_ORDER_FAIL("A00010", "没有抢到"),

    /**
     * 用户登录已过期
     */
    USER_LOGIN_EXPIRED("A0230", "用户登录已过期"),

    USER_REFRESH("A0240", "操作失败，请刷新页面重试"),


    PARAM_ERROR("B0001", "参数错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("Z0001", "系统错误"),

    /**
     * 功能未实现
     */
    SYSTEM_WARN("Z0002", "系统功能未实现");


    /**
     * 错误码
     */
    private final String code;

    /**
     * 中文描述
     */
    private final String message;

}
