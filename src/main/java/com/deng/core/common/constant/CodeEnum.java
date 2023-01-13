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
    OK("00000","一切正常");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 中文描述
     */
    private final String message;

}
