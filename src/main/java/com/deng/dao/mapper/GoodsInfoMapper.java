package com.deng.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.dao.entity.GoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.dto.req.GoodsOffReqDTO;
import com.deng.dto.req.GoodsPublicReqDTO;
import com.deng.dto.req.GoodsSearchReqDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.dto.resp.GoodsOffRespDTO;
import com.deng.dto.resp.GoodsPublicRespDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023/05/01
 */
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    /**
     * 商品搜索
     *
     * @param page      mybatis-plus 分页对象
     * @param condition 搜索条件
     * @return 返回结果
     */
    List<GoodsInfo> searchGoods(IPage<GoodsInfoRespDTO> page, GoodsSearchReqDTO condition);


    /**
     * 添加商品访问量
     *
     * @param goodsId
     */
    void addVisitCount(@Param("goodsId") Long goodsId);

    /**
     * 更新商品状态
     *
     * @param goodsId
     */
    int updateGoodsStatus(@Param("goodsId") Long goodsId);


    /**
     * 获取发布的商品列表
     *
     * @param page
     * @param condition
     * @return
     */
    List<GoodsInfo> getPublicGoods(IPage<GoodsPublicRespDTO> page, GoodsPublicReqDTO condition);

    /**
     * 获取下架的商品列表
     *
     * @param page
     * @param condition
     * @return
     */
    List<GoodsInfo> getOffGoods(Page<GoodsOffRespDTO> page, GoodsOffReqDTO condition);


}
