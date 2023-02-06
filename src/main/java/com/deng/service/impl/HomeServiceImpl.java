package com.deng.service.impl;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.resp.HomeGoodsRespDTO;
import com.deng.manage.cache.HomeGoodsCacheManager;
import com.deng.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :门户功能实现页面
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final HomeGoodsCacheManager HomeGoodsCacheManager;

    @Override
    public RestResp<List<HomeGoodsRespDTO>> listHomeGoods() {
        return RestResp.ok(HomeGoodsCacheManager.listHomeGoods());
    }
}
