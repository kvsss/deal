package com.deng.service;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.AdminGoodsCategoryUpdateReqDTO;
import com.deng.dto.req.AdminGoodsCategoryAddReqDTO;
import com.deng.dto.req.AdminGoodsCategoryReqDTO;
import com.deng.dto.resp.AdminGoodsCategoryRespDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface GoodsCategoryService {
    /**
     * 获取商品分类列表
     */
    public List<GoodsCategoryRespDTO> listCategory();

    /**
     * 获取所有商品分类
     * @param condition
     * @return
     */
    RestResp<PageRespDTO<AdminGoodsCategoryRespDTO>> getAllGoodsCategory(AdminGoodsCategoryReqDTO condition);

    /**
     * 添加商品分类
     * @param condition
     * @return
     */
    RestResp addGoodsCategory(AdminGoodsCategoryAddReqDTO condition);

    /**
     * 更新商品分类
     * @param condition
     * @return
     */
    RestResp updateGoodsCategory(AdminGoodsCategoryUpdateReqDTO condition);


    /**
     * 删除商品分类
     * @param uid
     * @param categoryId
     * @return
     */
    RestResp deleteGoodsCategory(Long uid, Long categoryId);
}
