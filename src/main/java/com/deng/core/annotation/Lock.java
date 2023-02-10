package com.deng.core.annotation;

import com.deng.core.common.constant.CodeEnum;

import java.lang.annotation.*;

/**
 * @author :deng
 * @version :1.0
 * @description : 锁的
 * @since :1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Lock {
    // 前缀
    String prefix();

    // 如果等待,就需waitTime
    boolean isWait() default false;

    long waitTime() default 3L;

    CodeEnum failCode() default CodeEnum.OK;
}