package com.deng.service;

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
}
