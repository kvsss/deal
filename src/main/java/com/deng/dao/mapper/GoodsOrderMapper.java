package com.deng.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.GoodsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.dao.entity.Transaction;
import com.deng.dto.req.GoodsBuyReqDTO;
import com.deng.dto.req.GoodsOffReqDTO;
import com.deng.dto.req.GoodsPlatformOrderReqDTO;
import com.deng.dto.req.GoodsSellReqDTO;
import com.deng.dto.resp.GoodsBuyRespDTO;
import com.deng.dto.resp.GoodsOffRespDTO;
import com.deng.dto.resp.GoodsPlatformOrderRespDTO;
import com.deng.dto.resp.GoodsSellRespDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 商品订单 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023/04/29
 */
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {

    /*  List<GoodsBuyRespDTO> getBuyGoods(Page<GoodsBuyRespDTO> page, GoodsBuyReqDTO condition);

      List<GoodsSellRespDTO> getSellGoods(Page<GoodsSellRespDTO> page, GoodsSellReqDTO condition);

      List<GoodsPlatformOrderRespDTO> getPlatformOrder(Page<GoodsPlatformOrderRespDTO> page, GoodsPlatformOrderReqDTO condition);*/

    /**
     * 获取最近12个天的交易额
     * @param start_date
     * @param end_date
     * @return
     */
    List<Transaction> getLast12DailyTransactions(@Param("start_date") LocalDate start_date,
                                                 @Param("end_date") LocalDate end_date);


    List<Transaction> getLast12WeeklyTransactions(@Param("start_date") LocalDate start_date,
                                                 @Param("end_date") LocalDate end_date);

    List<Transaction> getLast12MonthTransactions(@Param("start_date") LocalDate start_date,
                                                 @Param("end_date") LocalDate end_date);
}
