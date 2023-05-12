package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.exception.ServiceException;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.GoodsOrder;
import com.deng.dao.entity.Transaction;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dao.mapper.GoodsOrderMapper;
import com.deng.dao.mapper.UserInfoMapper;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;
import com.deng.manage.cache.GoodsInfoCacheManage;
import com.deng.service.GoodsInfoService;
import com.deng.service.GoodsOrderService;
import com.deng.service.GoodsRoleService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsOrderServiceImpl implements GoodsOrderService {
    @Resource
    private final GoodsOrderMapper goodsOrderMapper;

    private final GoodsInfoCacheManage goodsInfoCacheManage;

    private final GoodsInfoMapper goodsInfoMapper;

    private final UserInfoMapper userInfoMapper;

    private final GoodsInfoService goodsInfoService;

    private final GoodsRoleService goodsRoleService;

    @Override
    @Transactional
    public RestResp<Void> buyGoods(GoodsOrderAddReqDTO dto) {
        if (!goodsRoleService.isNormalUser()) {
            return RestResp.fail("该账号无法执行此操作");
        }

        // 行级索防止超卖
        if (goodsInfoMapper.updateGoodsStatus(dto.getGoodsId()) == 0) {
            return RestResp.fail(CodeEnum.USER_ORDER_FAIL);
        }

        // 添加商品订单信息
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsId(dto.getGoodsId());
        goodsOrder.setSellerId(dto.getSellerId());
        goodsOrder.setBuyerId(dto.getBuyerId());
        goodsOrder.setBuyerAddress(dto.getBuyerAddress());
        goodsOrder.setBuyerName(dto.getBuyerName());
        goodsOrder.setBuyerPhone(dto.getBuyerPhone());
        goodsOrder.setSellerPhone(userInfoMapper.selectById(dto.getSellerId()).getUsername());
        goodsOrder.setCreateTime(LocalDateTime.now());
        goodsOrder.setUpdateTime(LocalDateTime.now());
        goodsOrder.setPrice(dto.getPrice());
        goodsOrder.setStatus(0);
        goodsOrderMapper.insert(goodsOrder);
        return RestResp.ok();
    }

/*    @Override
    public RestResp<List<GoodsOrderRespDTO>> listOrder() {
        return null;
    }*/

    @Override
    public RestResp<Void> deleteOrder() {
        return null;
    }


    // 快速构建符合要求的Buyer QueryWrapper, status 标记为判断,status的值为期待标记位
    public QueryWrapper<GoodsOrder> getGoodsOrderQueryWrapperOfBuyer(Long userId, Integer status) {
        QueryWrapper<GoodsOrder> queryWrapper = new QueryWrapper<>();

        if (userId != null) {
            queryWrapper.eq(DateBaseConstants.GoodsOrderTable.COLUMN_BUYER_ID, userId);
        }

        queryWrapper.eq(DateBaseConstants.GoodsOrderTable.COLUMN_STATUS, status);
        return queryWrapper;
    }


    @Override
    public RestResp<PageRespDTO<GoodsBuyRespDTO>> getBuyGoods(GoodsBuyReqDTO condition) {

        // 分页
/*        Page<GoodsOrder> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        Page<GoodsOrder> goodsInfoPage;*/
        List<GoodsOrder> goodsOrders;

        // 查询已经购买的商品
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 1));*/

            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 1));

        }
        // 查询待收货
        else if ("2".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 0));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 0));

        }
        // 查询已经取消的
        else if ("3".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 2));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfBuyer(condition.getUid(), 2));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        //List<GoodsOrder> goodsOrdersInfos = goodsInfoPage.getRecords();
        List<GoodsOrder> goodsOrdersInfos = goodsOrders;
        List<GoodsBuyRespDTO> collect = goodsOrdersInfos.stream().map(goodsOrder -> {
                    GoodsInfo goodsInfo = goodsInfoMapper.selectOne(
                            goodsInfoService.getQueryWrapperForKeyword(goodsOrder.getGoodsId(), condition.getKeyword()));
                    // 这个地方
                    // 如果商品不存在或者商品的extra为0,则不返回
                    if (goodsInfo == null) {
                        return null;
                    }
                    return GoodsBuyRespDTO.builder()
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
                            .extra(goodsInfo.getExtra())

                            .orderId(goodsOrder.getId())
                            .sellerId(goodsOrder.getSellerId())
                            .buyerId(goodsOrder.getBuyerId())
                            .buyerPhone(goodsOrder.getBuyerPhone())
                            .sellerPhone(goodsOrder.getSellerPhone())
                            .buyerName(goodsOrder.getBuyerName())
                            .buyerAddress(goodsOrder.getBuyerAddress())
                            .completeTime(goodsOrder.getCompleteTime())
                            .createTime(goodsOrder.getCreateTime())
                            .updateTime(goodsOrder.getUpdateTime())
                            .status(goodsOrder.getStatus())
                            .build();
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        // 存在数量上的不对等
        int start = (condition.getPageNum() - 1) * condition.getPageSize();
        int end = start + condition.getPageSize();
        if (end > collect.size()) {
            end = collect.size();
        }

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(),
                collect.size(), collect.subList(start, end)));

    }


    // 快速构建符合要求的Buyer QueryWrapper, status 标记为判断,status的值为期待标记位
    public QueryWrapper<GoodsOrder> getGoodsOrderQueryWrapperOfSeller(Long userId, Integer status) {
        QueryWrapper<GoodsOrder> queryWrapper = new QueryWrapper<>();

        if (userId != null) {
            queryWrapper.eq(DateBaseConstants.GoodsOrderTable.COLUMN_SELLER_ID, userId);
        }
        queryWrapper.eq(DateBaseConstants.GoodsOrderTable.COLUMN_STATUS, status);

        return queryWrapper;
    }


    @Override
    public RestResp<PageRespDTO<GoodsSellRespDTO>> getSellGoods(GoodsSellReqDTO condition) {
        // 分页
/*        Page<GoodsOrder> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        Page<GoodsOrder> goodsInfoPage;*/
        List<GoodsOrder> goodsOrders;

        // 查询已经卖出的的商品
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 1));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 1));
        }
        // 查询待发货的
        else if ("2".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 0));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 0));
        }
        // 查询已经取消的
        else if ("3".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 2));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(condition.getUid(), 2));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        //List<GoodsOrder> goodsOrdersInfos = goodsInfoPage.getRecords();
        List<GoodsOrder> goodsOrdersInfos = goodsOrders;
        List<GoodsSellRespDTO> collect = goodsOrdersInfos.stream().map(goodsOrder -> {
                    GoodsInfo goodsInfo = goodsInfoMapper.selectOne(
                            goodsInfoService.getQueryWrapperForKeyword(goodsOrder.getGoodsId(), condition.getKeyword()));
                    // 这个地方
                    // 如果商品不存在或者商品的extra为0,则不返回
                    if (goodsInfo == null) {
                        return null;
                    }
                    return GoodsSellRespDTO.builder()
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
                            .extra(goodsInfo.getExtra())

                            .orderId(goodsOrder.getId())
                            .sellerId(goodsOrder.getSellerId())
                            .buyerId(goodsOrder.getBuyerId())
                            .buyerPhone(goodsOrder.getBuyerPhone())
                            .sellerPhone(goodsOrder.getSellerPhone())
                            .buyerName(goodsOrder.getBuyerName())
                            .buyerAddress(goodsOrder.getBuyerAddress())
                            .completeTime(goodsOrder.getCompleteTime())
                            .createTime(goodsOrder.getCreateTime())
                            .updateTime(goodsOrder.getUpdateTime())
                            .status(goodsOrder.getStatus())
                            .build();
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        // 存在数量上的不对等
        int start = (condition.getPageNum() - 1) * condition.getPageSize();
        int end = start + condition.getPageSize();
        if (end > collect.size()) {
            end = collect.size();
        }

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(),
                collect.size(), collect.subList(start, end)));

    }

    @Override
    public RestResp<PageRespDTO<GoodsPlatformOrderRespDTO>> getPlatformOrder(GoodsPlatformOrderReqDTO condition) {
        // 分页
        //Page<GoodsOrder> page = new Page<>();
        //page.setCurrent(condition.getPageNum());
        //page.setSize(condition.getPageSize());
        //Page<GoodsOrder> goodsInfoPage;
        List<GoodsOrder> goodsOrders;
        // 查询已经卖出的的商品
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 1));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 1));
        }
        // 查询待发货的
        else if ("2".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 0));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 0));

        }
        // 查询已经取消的
        else if ("3".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 2));*/
            goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 2));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        //List<GoodsOrder> goodsOrdersInfos = goodsInfoPage.getRecords();
        List<GoodsOrder> goodsOrdersInfos = goodsOrders;


        List<GoodsPlatformOrderRespDTO> collect = goodsOrdersInfos.stream().map(goodsOrder -> {
                    GoodsInfo goodsInfo = goodsInfoMapper.selectOne(
                            goodsInfoService.getQueryWrapperForKeyword(goodsOrder.getGoodsId(), condition.getKeyword()));
                    // 这个地方
                    // 如果商品不存在或者商品的extra为0,则不返回
                    if (goodsInfo == null || "0".equals(goodsInfo.getExtra())) {
                        return null;
                    }
                    return GoodsPlatformOrderRespDTO.builder()
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
                            .extra(goodsInfo.getExtra())

                            .orderId(goodsOrder.getId())
                            .sellerId(goodsOrder.getSellerId())
                            .buyerId(goodsOrder.getBuyerId())
                            .buyerPhone(goodsOrder.getBuyerPhone())
                            .sellerPhone(goodsOrder.getSellerPhone())
                            .buyerName(goodsOrder.getBuyerName())
                            .buyerAddress(goodsOrder.getBuyerAddress())
                            .completeTime(goodsOrder.getCompleteTime())
                            .createTime(goodsOrder.getCreateTime())
                            .updateTime(goodsOrder.getUpdateTime())
                            .status(goodsOrder.getStatus())
                            .build();
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        // 存在数量上的不对等
        int start = (condition.getPageNum() - 1) * condition.getPageSize();
        int end = start + condition.getPageSize();
        if (end > collect.size()) {
            end = collect.size();
        }

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(),
                collect.size(), collect.subList(start, end)));
    }


    // 快速构建符合要求的Buyer QueryWrapper, status 标记为判断,status的值为期待标记位
    public QueryWrapper<GoodsOrder> getGoodsOrderQueryWrapperInTrading(Long orderId) {
        QueryWrapper<GoodsOrder> goodsOrderQueryWrapper = new QueryWrapper<>();
        goodsOrderQueryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), orderId)
                .eq(DateBaseConstants.GoodsOrderTable.COLUMN_STATUS, 0);
        return goodsOrderQueryWrapper;
    }

    // 快速构建符合要求的Buyer QueryWrapper, status 标记为判断,status的值为期待标记位
    public QueryWrapper<GoodsInfo> getGoodsInfoQueryWrapperInTrading(Long goodsId) {
        QueryWrapper<GoodsInfo> goodsInfoQueryWrapper = new QueryWrapper<>();
        goodsInfoQueryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), goodsId)
                .eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 3);
        return goodsInfoQueryWrapper;
    }


    @Override
    @Transactional(rollbackFor = {ServiceException.class})
    public RestResp<Void> cancelOrder(Long orderId) {
        // 修改订单信息
        GoodsOrder goodsOrder = goodsOrderMapper.selectById(orderId);
        goodsOrder.setStatus(2);
        if (goodsOrderMapper.update(goodsOrder, getGoodsOrderQueryWrapperInTrading(orderId)) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }

        // 修改商品信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsOrder.getGoodsId());
        goodsInfo.setGoodsStatus(4);
        if (goodsInfoMapper.update(goodsInfo, getGoodsInfoQueryWrapperInTrading(goodsInfo.getId())) == 0) {
            // 这里需要回滚事务
            throw new ServiceException(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }

    @Override
    @Transactional(rollbackFor = {ServiceException.class})
    public RestResp<Void> finishOrder(Long orderId) {
        // 修改订单信息
        GoodsOrder goodsOrder = goodsOrderMapper.selectById(orderId);
        goodsOrder.setStatus(1);
        goodsOrder.setCompleteTime(LocalDateTime.now());
        if (goodsOrderMapper.update(goodsOrder, getGoodsOrderQueryWrapperInTrading(orderId)) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }


        // 修改商品信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsOrder.getGoodsId());
        goodsInfo.setGoodsStatus(1);
        if (goodsInfoMapper.update(goodsInfo, getGoodsInfoQueryWrapperInTrading(goodsInfo.getId())) == 0) {
            // 这里需要回滚事务
            throw new ServiceException(CodeEnum.USER_REFRESH);
        }

        // 添加用户成交数量
        UserInfo userInfo = userInfoMapper.selectById(goodsOrder.getSellerId());
        userInfo.setMakeCount(userInfo.getMakeCount() + 1);
        if (userInfoMapper.updateById(userInfo) == 0) {
            throw new ServiceException(CodeEnum.USER_REFRESH);
        }

        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateOrder(GoodsOrderUpdateRespDTO dto) {
        // 修改订单信息
        GoodsOrder goodsOrder = goodsOrderMapper.selectById(dto.getOrderId());
        goodsOrder.setBuyerPhone(dto.getBuyerPhone());
        goodsOrder.setBuyerAddress(dto.getBuyerAddress());


        if (goodsOrderMapper.update(goodsOrder, getGoodsOrderQueryWrapperInTrading(dto.getOrderId())) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }

        return RestResp.ok();
    }

    @Override
    public RestResp<PageRespDTO<AdminGoodsOrderRespDTO>> getAllGoodsOrder(AdminGoodsOrderReqDTO condition) {
        // 分页
        //Page<GoodsOrder> page = new Page<>();
        //page.setCurrent(condition.getPageNum());
        //page.setSize(condition.getPageSize());
        //Page<GoodsOrder> goodsInfoPage;
        List<GoodsOrder> goodsOrders;
        QueryWrapper<GoodsOrder> queryWrapper;
        // 查询已经卖出的的商品
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 1));*/
            queryWrapper = getGoodsOrderQueryWrapperOfSeller(null, 1);

            //goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 1));
        }
        // 查询待发货的
        else if ("2".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 0));*/
            queryWrapper = getGoodsOrderQueryWrapperOfSeller(null, 0);

            //goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 0));
        }
        // 查询已经取消的
        else if ("3".equals(condition.getExtra())) {
/*            goodsInfoPage = goodsOrderMapper.selectPage(page,
                    getGoodsOrderQueryWrapperOfSeller(null, 2));*/
            queryWrapper = getGoodsOrderQueryWrapperOfSeller(null, 2);

            //goodsOrders = goodsOrderMapper.selectList(getGoodsOrderQueryWrapperOfSeller(null, 2));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        // 模糊匹配
        if (condition.getKeyword() != null && !"".equals(condition.getKeyword())) {
            queryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), condition.getKeyword());
        }

        List<GoodsOrder> goodsOrdersInfos = goodsOrderMapper.selectList(queryWrapper);
        List<AdminGoodsOrderRespDTO> collect = goodsOrdersInfos.stream().map(goodsOrder -> {

                    GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsOrder.getGoodsId());
                  /*  GoodsInfo goodsInfo = goodsInfoMapper.selectOne(
                            goodsInfoService.getQueryWrapperForKeyword(goodsOrder.getGoodsId(), condition.getKeyword()));*/
                    // 这个地方
                    // 如果商品不存在或者商品的extra为0,则不返回
                    if (goodsInfo == null) {
                        return null;
                    }
                    return AdminGoodsOrderRespDTO.builder()
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
                            .extra(goodsInfo.getExtra())

                            .orderId(goodsOrder.getId())
                            .sellerId(goodsOrder.getSellerId())
                            .buyerId(goodsOrder.getBuyerId())
                            .buyerPhone(goodsOrder.getBuyerPhone())
                            .sellerPhone(goodsOrder.getSellerPhone())
                            .buyerName(goodsOrder.getBuyerName())
                            .buyerAddress(goodsOrder.getBuyerAddress())
                            .completeTime(goodsOrder.getCompleteTime())
                            .createTime(goodsOrder.getCreateTime())
                            .updateTime(goodsOrder.getUpdateTime())
                            .status(goodsOrder.getStatus())
                            .build();
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        // 存在数量上的不对等
        int start = (condition.getPageNum() - 1) * condition.getPageSize();
        int end = start + condition.getPageSize();
        if (end > collect.size()) {
            end = collect.size();
        }

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(),
                collect.size(), collect.subList(start, end)));
    }


    @Override
    public RestResp<AdminOrderSummaryRespDTO> getOrderSummary(AdminOrderSummaryReqDTO condition) {
        LocalDate now = LocalDate.now();
        int sum = 12;
        List<Transaction> result;
        // 周
        if (condition.getExtra() == null || "".equals(condition.getExtra()) || "1".equals(condition.getExtra())) {
            result = goodsOrderMapper.getLast12DailyTransactions(now.minusDays(sum - 1), now.plusDays(1));
            if (result.size() != sum) {
                LocalDate start = now.minusDays(sum - 1);
                LocalDate end = now.plusDays(1);
                int index = 0;
                for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                    boolean found = false;
                    if (result.size() == index || !result.get(index).getDay().equals(date)) {
                        //index++;
                        Transaction t = new Transaction();
                        t.setDay(date);
                        t.setDayDate(null);
                        t.setAmounts(BigDecimal.ZERO);
                        t.setCounts(0L);
                        result.add(index, t);
                    }
                    index++;
                }
            }
            AdminOrderSummaryRespDTO resp = new AdminOrderSummaryRespDTO();
            String[] titles = new String[sum];
            Long[] orderCounts = new Long[sum];
            Double[] orderAmounts = new Double[sum];

            for (int i = 0; i < result.size(); i++) {
                titles[i] = result.get(i).getDay().toString();
                orderCounts[i] = result.get(i).getCounts();
                orderAmounts[i] = result.get(i).getAmounts().doubleValue();
            }
            resp.setOrderAmounts(orderAmounts);
            resp.setOrderCounts(orderCounts);
            resp.setTitles(titles);
            return RestResp.ok(resp);
        }// 月
        else if ("2".equals(condition.getExtra())) {
            //result = goodsOrderMapper.getLast12WeeklyTransactions(now.minusWeeks(sum), now);
            //int a = 1;
        }// 年
        else if ("3".equals(condition.getExtra())) {
            result = goodsOrderMapper.getLast12MonthTransactions(now.minusMonths(sum), now);
            if (result.size() != sum) {
                LocalDate start = now.minusMonths(sum - 1);
                LocalDate end = now.minusMonths(1);

                int index = 0;
                for (LocalDate date = start; date.isBefore(end); date = date.plusMonths(1)) {

                    if (result.size() == index || !result.get(index).getDayDate().equals(YearMonth.from(date))) {
                        //index++;
                        Transaction t = new Transaction();
                        t.setDay(date);
                        t.setDayDate(YearMonth.from(date));
                        t.setAmounts(BigDecimal.ZERO);
                        t.setCounts(0L);
                        result.add(index, t);
                    }
                    index++;
                }
            }

            AdminOrderSummaryRespDTO resp = new AdminOrderSummaryRespDTO();
            String[] titles = new String[sum];
            Long[] orderCounts = new Long[sum];
            Double[] orderAmounts = new Double[sum];

            for (int i = 0; i < result.size(); i++) {
                titles[i] = result.get(i).getDayDate().toString();
                orderCounts[i] = result.get(i).getCounts();
                orderAmounts[i] = result.get(i).getAmounts().doubleValue();
            }
            resp.setOrderAmounts(orderAmounts);
            resp.setOrderCounts(orderCounts);
            resp.setTitles(titles);
            return RestResp.ok(resp);
        } else {
            RestResp.fail("参数错误");
        }
        return RestResp.fail("参数错误");
    }


    @Override
    public RestResp<SellerRespDTO> getSeller(Long goodsId) {

        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        UserInfo userInfo = userInfoMapper.selectById(goodsInfo.getUid());

        return RestResp.ok(SellerRespDTO.builder().nickName(userInfo.getNickName())
                .userPhoto(userInfo.getUserPhoto())
                .publicCount(userInfo.getPublicCount())
                .makeCount(userInfo.getMakeCount())
                .build());
    }
}
