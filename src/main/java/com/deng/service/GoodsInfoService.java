package com.deng.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :商品服务类
 * @since :1.8
 */
public interface GoodsInfoService {

    /**
     * 商品信息保存
     *
     * @param dto 商品信息
     * @return void
     */
    RestResp<Void> saveGoods(GoodsAddReqDTO dto);


    /**
     * 商品分类列表查询
     *
     * @return 商品分类列表
     */
    RestResp<List<GoodsCategoryRespDTO>> listCategory();


    /**
     * 商品信息查询
     *
     * @param goodsId
     * @return
     */
    RestResp<GoodsInfoRespDTO> getGoodsById(Long goodsId);

    /**
     * 商品访问量查询
     *
     * @param goodsId
     * @return
     */
    RestResp<Void> addVisitCount(Long goodsId);


    /**
     * 商品评论列表
     *
     * @param goodsId
     * @return
     */
    RestResp<GoodsCommentRespDTO> listNewestComments(Long goodsId);


    /**
     * 用户评论商品
     *
     * @param dto
     * @return
     */
    RestResp<Void> saveComment(UserCommentReqDTO dto);

    /**
     * 修改评论
     *
     * @param uid
     * @param id
     * @param content
     * @return
     */
    RestResp<Void> updateComment(Long uid, Long id, String content);

    /**
     * 删除评论
     *
     * @param uid
     * @param id
     * @return
     */
    RestResp<Void> deleteComment(Long uid, Long id);

    /**
     * 获取用户发布商品接口
     *
     * @param dto
     * @return
     */
    RestResp<PageRespDTO<GoodsPublicRespDTO>> getPublicGoods(GoodsPublicReqDTO condition);

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    RestResp<Void> deleteGoods(Long goodsId);

    /**
     * 上架商品
     *
     * @param goodsId
     * @return
     */
    RestResp<Void> offShelfGoods(Long goodsId);

    /**
     * 获得下架商品信息
     *
     * @param condition
     * @return
     */
    RestResp<PageRespDTO<GoodsOffRespDTO>> getOffGoods(GoodsOffReqDTO condition);

    /**
     * 上架商品
     *
     * @param goodsId
     * @return
     */
    RestResp<Void> onShelfGoods(Long goodsId);

    /**
     * 修改商品信息
     *
     * @param dto
     * @return
     */
    RestResp<Void> updateGoods(GoodsUpdateRespDTO dto);

    /**
     * 获取用户申请商品列表
     *
     * @param condition
     * @return
     */
    RestResp<PageRespDTO<GoodsApplyRespDTO>> getApplyGoods(GoodsApplyReqDTO condition);


    QueryWrapper<GoodsInfo> getQueryWrapperForStatus(Long goodsId, Integer status);

    QueryWrapper<GoodsInfo> getQueryWrapperForKeyword(Long goodsId, String keyword);

    QueryWrapper<GoodsInfo> getQueryWrapperForStatusAndKeyword(Long goodsId, Integer status, String keyword);


    RestResp<Void> agreeApplyGoods(Long uid, Long goodsId);

    RestResp<Void> disagreeApplyGoods(Long uid, Long goodsId);

    RestResp<Void> platformOffGoods(Long uid, Long goodsId);

    RestResp<PageRespDTO<GoodsPlatformRespDTO>> getPlatformGoods(GoodsPlatformReqDTO condition);

    RestResp<PageRespDTO<AdminGoodsRespDTO>> getAllGoods(AdminGoodsReqDTO condition);


    RestResp adminOffGoods(Long uid, Long goodsId);

    RestResp<Void> adminDeleteGoods(Long uid, Long goodsId);
}
