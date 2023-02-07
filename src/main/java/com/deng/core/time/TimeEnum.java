package com.deng.core.time;

/**
 * @author :deng
 * @version :1.0
 * @description : 时间枚举
 * @since :1.8
 */
public enum TimeEnum {
    /**
     * 一天
     */
    DAY_1(60 * 60 * 24),
    /**
     * 一小时
     */
    HOUR_1(60 * 60),
    /**
     * 一分钟
     */
    MINUTE_1(60);

    /**
     * 时间
     */
    private final int time;

    TimeEnum(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}