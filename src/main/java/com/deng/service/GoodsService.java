package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.GoodsAddReqDTO;
import com.deng.dto.req.UserCommentReqDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import com.deng.dto.resp.GoodsCommentRespDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.dto.resp.HomeGoodsRespDTO;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :商品服务类
 * @since :1.8
 */
public interface GoodsService {

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
}
