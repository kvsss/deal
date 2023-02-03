package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.GoodsAddReqDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :商品服务类
 * @since :1.8
 */
public interface GoodsService {

    /**
     * 商品信息保存
     *
     * @param dto 商品信息
     * @return void
     */
    RestResp<Void> saveGoods(GoodsAddReqDTO dto);


    /**
     * 商品分类列表查询
     * @return 商品分类列表
     */
    RestResp<List<GoodsCategoryRespDTO>> listCategory();
}
