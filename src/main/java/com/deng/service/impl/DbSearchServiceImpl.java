package com.deng.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author :deng
 * @version :1.0
 * @description : 数据库搜索实现类
 * @since :1.8
 */
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "false", matchIfMissing = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class DbSearchServiceImpl implements SearchService {

    private final GoodsInfoMapper goodsInfoMapper;

    @Override
    public RestResp<PageRespDTO<GoodsInfoRespDTO>> search(GoodsSearchReqDTO condition) {
        Page<GoodsInfoRespDTO> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());

        List<GoodsInfo> goodsInfos = goodsInfoMapper.searchGoods(page, condition);
        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> GoodsInfoRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .uid(goodsInfo.getUid())
                        .build()
                ).collect(Collectors.toList())
        ));
    }
}
