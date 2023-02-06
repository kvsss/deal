package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.resp.HomeGoodsRespDTO;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :门户服务类
 * @since :1.8
 */
public interface HomeService {

    /**
     * 查询首页商品推荐列表
     * @return 返回视屏推荐信息
     */
    RestResp<List<HomeGoodsRespDTO>> listHomeGoods();
}
