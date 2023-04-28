package com.deng.core.constant;

import lombok.Getter;

/**
 * @author :deng
 * @version :1.0
 * @description :数组库常量(sql使用)
 * @since :1.8
 */
public final class DateBaseConstants {
    public static final class UserInfoTable {
        private UserInfoTable() {
            throw new IllegalStateException(this.getClass().getName() + ":" + SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USERNAME = "username";
    }

    public static final class GoodsInfoTable {
        private GoodsInfoTable() {
            throw new IllegalStateException(this.getClass().getName() + ":" + SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_VISIT_COUNT = "visit_count";
    }


    public static final class GoodsCommentTable {
        private GoodsCommentTable() {
            throw new IllegalStateException(this.getClass().getName() + ":" + SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_GOODS_ID = "goods_id";
        public static final String COLUMN_USER_ID = "user_id";
    }


    @Getter
    public enum CommonColumnEnum {
        // id
        ID("id"),
        // sort
        SORT("sort"),
        // 创建时间
        CREATE_TIME("create_time"),
        // 修改时间
        UPDATE_TIME("update_time");

        private final String name;

        CommonColumnEnum(String name) {
            this.name = name;
        }
    }


    public enum LimitSQLtEnum {
        // 数量限制为1
        LIMIT_1("limit 1"),
        // 数量限制为2
        LIMIT_2("limit 2"),
        // 数量限制为5
        LIMIT_5("limit 5"),
        // 数量限制为10
        LIMIT_10("limit 10"),
        // 数量限制为12
        LIMIT_12("limit 12"),
        // 数量限制为30
        LIMIT_30("limit 30"),
        // 数量限制为50
        LIMIT_50("limit 50"),
        // 数量限制为100
        LIMIT_100("limit 50"),
        // 数量限制为500
        LIMIT_500("limit 500");

        private final String limitSql;

        LimitSQLtEnum(String limitSql) {
            this.limitSql = limitSql;
        }

        public String getLimitSql() {
            return limitSql;
        }
    }


}
