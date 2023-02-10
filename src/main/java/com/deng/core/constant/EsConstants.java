package com.deng.core.constant;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author :deng
 * @version :1.0
 * @description : Es常量
 * @since :1.8
 */
public class EsConstants {
    private EsConstants() {
        throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
    }


    public static class GoodsIndex {
        private GoodsIndex() {
            throw new IllegalStateException(SystemConfigConstants.CONST_INSTANCE_EXCEPTION_MSG);
        }

        /**
         * 索引名
         */
        public static final String INDEX_NAME = "goods";

        /**
         * id
         */
        public static final String FIELD_ID = "id";

        /**
         * 类别ID
         */
        public static final String FIELD_CATEGORY_ID = "categoryId";

        /**
         * 类别名
         */
        public static final String FIELD_CATEGORY_NAME = "categoryName";

        /**
         * 商品封面地址
         */
        public static final String FIELD_PIC_URL = "picUrl";

        /**
         * 用户id
         */
        public static final String FIELD_UID = "uid";

        /**
         * 用户名
         */
        public static final String FIELD_NICKNAME = "nickName";

        /**
         * 商品价格
         */
        public static final String FIELD_GOODS_PRICE = "goodsPrice";

        /**
         * 商品标题
         */
        public static final String FIELD_GOODS_TITLE = "goodsTitle";

        /**
         * 商品描述
         */
        public static final String FIELD_GOODS_CONTENT = "goodsContent";

        /**
         * 点击量
         */
        public static final String FIELD_VISIT_COUNT = "visitCount";

        /**
         * 评论数
         */
        public static final String FIELD_COMMENT_COUNT = "commentCount";

        /**
         * 创建时间
         */
        public static final String FIELD_CREATE_TIME = "createTime";

        /**
         * 更新时间
         */
        public static final String FIELD_UPDATE_TIME = "updateTime";
    }

}
