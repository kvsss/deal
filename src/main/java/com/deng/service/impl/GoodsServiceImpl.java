package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsComment;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.mapper.GoodsCommentMapper;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dto.req.GoodsAddReqDTO;
import com.deng.dto.req.UserCommentReqDTO;
import com.deng.dto.resp.GoodsCategoryRespDTO;
import com.deng.dto.resp.GoodsCommentRespDTO;
import com.deng.dto.resp.GoodsInfoRespDTO;
import com.deng.dto.resp.HomeGoodsRespDTO;
import com.deng.manage.cache.GoodsCategoryCacheManage;
import com.deng.manage.cache.GoodsInfoCacheManage;
import com.deng.manage.dao.UserDaoManager;
import com.deng.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
public class GoodsServiceImpl implements GoodsService {

    private final GoodsCategoryCacheManage goodsCategoryCacheManage;

    private final GoodsInfoMapper goodsInfoMapper;

    private final GoodsInfoCacheManage goodsInfoCacheManage;

    private final GoodsCommentMapper goodsCommentMapper;

    private final UserDaoManager userDaoManager;


    @Override
    public RestResp<Void> saveGoods(GoodsAddReqDTO dto) {
        // 校验商品名是否已存在
        GoodsInfo goodsInfo = new GoodsInfo();
        // 填充数据
        goodsInfo.setCategoryId(dto.getCategoryId());
        goodsInfo.setCategoryName(dto.getCategoryName());
        goodsInfo.setPicUrl(dto.getPicUrl());
        goodsInfo.setUid(dto.getUid());
        goodsInfo.setNickName(dto.getNickName());
        goodsInfo.setGoodsPrice(dto.getPrice());
        goodsInfo.setGoodsTitle(dto.getGoodsTitle());
        goodsInfo.setGoodsContent(dto.getGoodsContent());
        goodsInfo.setCreateTime(LocalDateTime.now());
        goodsInfo.setUpdateTime(LocalDateTime.now());
        goodsInfoMapper.insert(goodsInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return RestResp.ok(goodsCategoryCacheManage.listCategory());
    }

    @Override
    public RestResp<GoodsInfoRespDTO> getGoodsById(Long goodsId) {
        return RestResp.ok(goodsInfoCacheManage.getGoodsById(goodsId));
    }

    @Override
    public RestResp<Void> addVisitCount(Long goodsId) {
        goodsInfoMapper.addVisitCount(goodsId);
        return RestResp.ok();
    }

    @Override
    public RestResp<GoodsCommentRespDTO> listNewestComments(Long goodsId) {
        // 查询评论总数
        QueryWrapper<GoodsComment> commentCountQueryWrapper = new QueryWrapper<>();
        commentCountQueryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_GOODS_ID, goodsId);
        // 查中的数量
        Long commentTotal = goodsCommentMapper.selectCount(commentCountQueryWrapper);
        GoodsCommentRespDTO goodsCommentRespDTO = GoodsCommentRespDTO.builder()
                .commentTotal(commentTotal).build();
        if (commentTotal > 0) {

            // 查询最新的评论列表
            QueryWrapper<GoodsComment> commentQueryWrapper = new QueryWrapper<>();
            // 查信息
            commentQueryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_GOODS_ID, goodsId)
                    .orderByDesc(DateBaseConstants.CommonColumnEnum.CREATE_TIME.getName())
                    .last(DateBaseConstants.LimitSQLtEnum.LIMIT_5.getLimitSql());
            List<GoodsComment> goodsComments = goodsCommentMapper.selectList(commentQueryWrapper);

            // 查询评论用户信息，并设置需要返回的评论用户名
            // 查询数据库
            List<Long> userIds = goodsComments.stream().map(GoodsComment::getUserId).collect(Collectors.toList());

            // 查询用户信息
            List<UserInfo> userInfos = userDaoManager.listUsers(userIds);
            Map<Long, UserInfo> userInfoMap = userInfos.stream()
                    .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
            List<GoodsCommentRespDTO.CommentInfo> commentInfos = goodsComments.stream()
                    .map(v -> GoodsCommentRespDTO.CommentInfo.builder()
                            .id(v.getId())
                            .commentUserId(v.getUserId())
                            .commentUser(userInfoMap.get(v.getUserId()).getUsername())
                            .commentUserPhoto(userInfoMap.get(v.getUserId()).getUserPhoto())
                            .commentContent(v.getCommentContent())
                            .commentTime(v.getCreateTime()).build()).collect(Collectors.toList());
            goodsCommentRespDTO.setComments(commentInfos);
        } else {
            goodsCommentRespDTO.setComments(Collections.emptyList());
        }
        return RestResp.ok(goodsCommentRespDTO);
    }


    @Override
    public RestResp<Void> saveComment(UserCommentReqDTO dto) {
        // 校验用户是否已发表评论
        QueryWrapper<GoodsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_USER_ID, dto.getUserId())
                .eq(DateBaseConstants.GoodsCommentTable.COLUMN_GOODS_ID, dto.getGoodsId());
        if (goodsCommentMapper.selectCount(queryWrapper) > 0) {
            // 用户已发表评论
            return RestResp.fail(CodeEnum.USER_COMMENTED);
        }
        // 数据封装
        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setGoodsId(dto.getGoodsId());
        goodsComment.setUserId(dto.getUserId());
        goodsComment.setCommentContent(dto.getCommentContent());
        goodsComment.setCreateTime(LocalDateTime.now());
        goodsComment.setUpdateTime(LocalDateTime.now());
        goodsCommentMapper.insert(goodsComment);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateComment(Long uid, Long id, String content) {
        QueryWrapper<GoodsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_USER_ID, uid)
                .eq(DateBaseConstants.CommonColumnEnum.ID.getName(), id);

        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setCommentContent(content);
        goodsCommentMapper.update(goodsComment, queryWrapper);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteComment(Long uid, Long id) {
        QueryWrapper<GoodsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_USER_ID, uid)
                .eq(DateBaseConstants.CommonColumnEnum.ID.getName(), id);
        goodsCommentMapper.delete(queryWrapper);
        return RestResp.ok();
    }
}
