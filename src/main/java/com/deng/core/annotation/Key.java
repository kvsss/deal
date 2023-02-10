package com.deng.core.annotation;

import java.lang.annotation.*;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Key {
    String expr() default "";
}
