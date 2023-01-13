package com.deng.service;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;

/**
 * @author :deng
 * @version :1.0
 * @description : 搜索服务类
 * @since :1.8
 */
public interface SearchService {

    /**
     * 商品查询
     * @param : condition 搜索条件
     * @return : 收搜结果
     */
    RestResp<PageRespDTO<GoodsInfoRespDTO>> search(GoodsSearchReqDTO condition);
}
