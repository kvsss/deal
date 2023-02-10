package com.deng.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.EsConstants;
import com.deng.dto.es.EsGoodsDTO;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author :deng
 * @version :1.0
 * @description : elasticsearch 搜索实现类
 * @since :1.8
 */
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
@Slf4j
public class EsSearchServiceImpl implements SearchService {

    private final ElasticsearchClient esClient;

    @Override
    @SneakyThrows
    public RestResp<PageRespDTO<GoodsInfoRespDTO>> search(GoodsSearchReqDTO condition) {

        SearchResponse<EsGoodsDTO> response = esClient.search(s -> {

                    SearchRequest.Builder searchBuilder = s.index(EsConstants.GoodsIndex.INDEX_NAME);
                    // 构建检索条件
                    buildSearchCondition(condition, searchBuilder);
                    // 排序
                    if (!StringUtils.isBlank(condition.getSort())) {
                        searchBuilder.sort(o -> o.field(f -> f
                                .field(StringUtils.underlineToCamel(condition.getSort().split(" ")[0]))
                                .order(SortOrder.Desc))
                        );
                    }
                    // 分页
                    searchBuilder.from((condition.getPageNum() - 1) * condition.getPageSize())
                            .size(condition.getPageSize());
                    // 设置高亮显示
                    searchBuilder.highlight(h -> h.fields(EsConstants.GoodsIndex.FIELD_GOODS_TITLE,
                            t -> t.preTags("<em style='color:red'>").postTags("</em>"))
                            .fields(EsConstants.GoodsIndex.FIELD_GOODS_CONTENT,
                                    t -> t.preTags("<em style='color:red'>").postTags("</em>")));

                    return searchBuilder;
                },
                EsGoodsDTO.class
        );

        TotalHits total = response.hits().total();

        List<GoodsInfoRespDTO> list = new ArrayList<>();
        List<Hit<EsGoodsDTO>> hits = response.hits().hits();
        // 类型推断 var 非常适合 for 循环，JDK 10 引入，JDK 11 改进

        for (Hit<EsGoodsDTO> hit : hits) {
            EsGoodsDTO goods = hit.source();
            assert goods != null;
            if (!CollectionUtils.isEmpty(hit.highlight().get(EsConstants.GoodsIndex.FIELD_GOODS_TITLE))) {
                goods.setGoodsTitle(hit.highlight().get(EsConstants.GoodsIndex.FIELD_GOODS_TITLE).get(0));
            }
            if (!CollectionUtils.isEmpty(
                    hit.highlight().get(EsConstants.GoodsIndex.FIELD_GOODS_CONTENT))) {
                goods.setGoodsContent(
                        hit.highlight().get(EsConstants.GoodsIndex.FIELD_GOODS_CONTENT).get(0));
            }
            list.add(GoodsInfoRespDTO.builder()
                    .goodsId(goods.getId())
                    .goodsContent(goods.getGoodsContent())
                    .goodsTitle(goods.getGoodsTitle())
                    .nickName(goods.getNickName())
                    .picUrl(goods.getPicUrl())
                    .price(goods.getGoodsPrice())
                    .build());
        }
        assert total != null;
        return RestResp.ok(
                PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), total.value(), list));

    }

    /**
     * 构建检索条件
     */
    private void buildSearchCondition(GoodsSearchReqDTO condition,
                                      SearchRequest.Builder searchBuilder) {
        BoolQuery boolQuery = BoolQuery.of(b -> {
            // 只查有字数的小说
/*            b.must(RangeQuery.of(m -> m
                    .field(EsConstants.GoodsIndex.FIELD_WORD_COUNT)
                    .gt(JsonData.of(0))
            )._toQuery());*/

            if (!StringUtils.isBlank(condition.getKeyword())) {
                // 关键词匹配 title 和内容
                b.must((q -> q.multiMatch(t -> t
                        .fields(EsConstants.GoodsIndex.FIELD_GOODS_TITLE + "^2",
                                EsConstants.GoodsIndex.FIELD_GOODS_CONTENT + "^1.8")
                        .query(condition.getKeyword()))
                ));
            }

            // 类别
            if (Objects.nonNull(condition.getCategoryId())) {
                b.must(TermQuery.of(m -> m
                        .field(EsConstants.GoodsIndex.FIELD_CATEGORY_ID)
                        .value(condition.getCategoryId())
                )._toQuery());
            }

            // 时间
            if (Objects.nonNull(condition.getPublishTimeMin())) {
                b.must(RangeQuery.of(m -> m
                        .field(EsConstants.GoodsIndex.FIELD_CREATE_TIME)
                        .gte(JsonData.of(condition.getPublishTimeMin().getTime()))
                )._toQuery());
            }
            return b;
            // 范围查询
            // 字数最小
/*            if (Objects.nonNull(condition.getWordCountMin())) {
                b.must(RangeQuery.of(m -> m
                        .field(EsConsts.BookIndex.FIELD_WORD_COUNT)
                        .gte(JsonData.of(condition.getWordCountMin()))
                )._toQuery());
            }

            // 字数最大
            if (Objects.nonNull(condition.getWordCountMax())) {
                b.must(RangeQuery.of(m -> m
                        .field(EsConsts.BookIndex.FIELD_WORD_COUNT)
                        .lt(JsonData.of(condition.getWordCountMax()))
                )._toQuery());
            }*/

        });
        searchBuilder.query(q -> q.bool(boolQuery));
    }


}
