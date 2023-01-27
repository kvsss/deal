package com.deng.service.impl;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author :deng
 * @version :1.0
 * @description : elasticsearch 搜索实现类
 * @since :1.8
 */
@ConditionalOnProperty(prefix="spring.elasticsearch",name="enabled",havingValue="true")
@Service
@RequiredArgsConstructor
@Slf4j
public class EsSearchServiceImpl implements SearchService {
    @Override
    public RestResp<PageRespDTO<GoodsInfoRespDTO>> search(GoodsSearchReqDTO condition) {
        return null;
    }
}
