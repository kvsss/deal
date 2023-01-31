package com.deng.core.auth;

import lombok.experimental.UtilityClass;

/**
 * @author :deng
 * @version :1.0
 * @description :用户信息持有
 * @since :1.8
 */
@UtilityClass
public class UserHolder {
    /**
     * 当前线程用户ID
     */
    private static final ThreadLocal<Long> userIdTL = new ThreadLocal<Long>();

    public static Long getUserId() {
        return userIdTL.get();
    }

    public static void setUserId(Long id) {
        userIdTL.set(id);
    }

    public static void clear() {
        userIdTL.remove();
    }
}
