package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.core.annotation.Key;
import com.deng.core.annotation.Lock;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.dao.entity.GoodsComment;
import com.deng.dao.entity.GoodsInfo;
import com.deng.dao.entity.GoodsOrder;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.mapper.GoodsCommentMapper;
import com.deng.dao.mapper.GoodsInfoMapper;
import com.deng.dao.mapper.UserInfoMapper;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;
import com.deng.manage.cache.GoodsCategoryCacheManage;
import com.deng.manage.cache.GoodsInfoCacheManage;
import com.deng.manage.dao.UserDaoManager;
import com.deng.service.GoodsCategoryService;
import com.deng.service.GoodsInfoService;
import com.deng.service.GoodsRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private final GoodsCategoryCacheManage goodsCategoryCacheManage;

    private final GoodsInfoMapper goodsInfoMapper;

    private final GoodsInfoCacheManage goodsInfoCacheManage;

    private final GoodsCommentMapper goodsCommentMapper;

    private final UserDaoManager userDaoManager;

    private final UserInfoMapper userInfoMapper;

    private final GoodsCategoryService goodsCategoryService;

    private final GoodsRoleService goodsRoleService;


    @Override
    public RestResp<Void> saveGoods(GoodsAddReqDTO dto) {
        if (!goodsRoleService.isNormalUser()) {
            return RestResp.fail("该账号无法执行此操作");
        }

        // 修改时间戳,去掉时分秒
        dto.setBuyTime(dto.getBuyTime().toLocalDate().atStartOfDay());

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
        goodsInfo.setBuyTime(dto.getBuyTime());
        goodsInfo.setOldDegree(dto.getOldDegree());

        if (dto.getExtra() == null || "0".equals(dto.getExtra())) {
            goodsInfo.setGoodsStatus(0);
            dto.setExtra(dto.getExtra());
        } else if ("1".equals(dto.getExtra())) {
            // 11:表示需要平台审核
            goodsInfo.setGoodsStatus(11);
            dto.setExtra(dto.getExtra());
        } else {
            return RestResp.fail(CodeEnum.PARAM_ERROR);
        }
        goodsInfo.setExtra(dto.getExtra());

        // 更新用户上架数量
        UserInfo userInfo = userInfoMapper.selectById(dto.getUid());
        userInfo.setPublicCount(userInfo.getPublicCount() + 1);
        userInfoMapper.updateById(userInfo);

        goodsInfoMapper.insert(goodsInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<List<GoodsCategoryRespDTO>> listCategory() {
        return RestResp.ok(goodsCategoryService.listCategory());
    }

    @Override
    public RestResp<GoodsInfoRespDTO> getGoodsById(Long goodsId) {
        // 查询基础信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        GoodsInfoRespDTO result = GoodsInfoRespDTO.builder().
                goodsId(goodsInfo.getId())
                .price(goodsInfo.getGoodsPrice())
                .goodsTitle(goodsInfo.getGoodsTitle())
                .picUrl(goodsInfo.getPicUrl())
                .goodsContent(goodsInfo.getGoodsContent())
                .nickName(goodsInfo.getNickName())
                .goodsStatus(goodsInfo.getGoodsStatus())
                .buyTime(goodsInfo.getBuyTime())
                .oldDegree(goodsInfo.getOldDegree())
                .uid(goodsInfo.getUid())
                .extra(goodsInfo.getExtra())
                .build();
        return RestResp.ok(result);
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

    /**
     * 这里实现加锁
     *
     * @param dto
     * @return
     */
    @Lock(prefix = "userComment")
    @Override
    public RestResp<Void> saveComment(
            @Key(expr = "#{userId + '::' + goodsId}") UserCommentReqDTO dto) {
        if (!goodsRoleService.isNormalUser()) {
            return RestResp.fail("该账号无法执行此操作");
        }

        // 校验用户是否已发表评论
        QueryWrapper<GoodsComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsCommentTable.COLUMN_USER_ID, dto.getUserId())
                .eq(DateBaseConstants.GoodsCommentTable.COLUMN_GOODS_ID, dto.getGoodsId());

        // 这里需要判断加锁
        if (goodsCommentMapper.selectCount(queryWrapper) > 0) {
            // 用户已发表评论
            return RestResp.fail(CodeEnum.USER_COMMENTED);
        }

        // 评论数量加1
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(dto.getGoodsId());
        goodsInfo.setCommentCount(goodsInfo.getCommentCount() + 1);
        goodsInfoMapper.updateById(goodsInfo);

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


    @Override
    public RestResp<PageRespDTO<GoodsPublicRespDTO>> getPublicGoods(GoodsPublicReqDTO condition) {
        Page<GoodsInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        Page<GoodsInfo> goodsInfoPage;
        // 发布的
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
            goodsInfoPage = goodsInfoMapper.selectPage(page,
                    getQueryWrapperForStatusAndKeyword(condition.getUid(), 0, condition.getKeyword()));
        }
        // 平台待审核中
        else if ("2".equals(condition.getExtra())) {
            goodsInfoPage = goodsInfoMapper.selectPage(page,
                    getQueryWrapperForStatusAndKeyword(condition.getUid(), 11, condition.getKeyword()));
        }
        // 平台审核不通过
        else if ("3".equals(condition.getExtra())) {
            goodsInfoPage = goodsInfoMapper.selectPage(page,
                    getQueryWrapperForStatusAndKeyword(condition.getUid(), 20, condition.getKeyword()));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        List<GoodsInfo> goodsInfos = goodsInfoPage.getRecords();
        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> GoodsPublicRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .categoryId(goodsInfo.getCategoryId())
                        .categoryName(goodsInfo.getCategoryName())
                        .createTime(goodsInfo.getCreateTime())
                        .updateTime(goodsInfo.getUpdateTime())
                        .uid(goodsInfo.getUid())
                        .extra(goodsInfo.getExtra())
                        .build()
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp<PageRespDTO<GoodsOffRespDTO>> getOffGoods(GoodsOffReqDTO condition) {
        Page<GoodsInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        Page<GoodsInfo> goodsInfoPage;

        QueryWrapper<GoodsInfo> queryWrapper = getQueryWrapperForStatusAndKeyword(condition.getUid(), 2, condition.getKeyword());
        // 个人下架的
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
            queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_EXTRA, 0);
        }
        // 平台下架的
        else if ("2".equals(condition.getExtra())) {
            queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_EXTRA, 1);
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        goodsInfoPage = goodsInfoMapper.selectPage(page, queryWrapper);
        List<GoodsInfo> goodsInfos = goodsInfoPage.getRecords();

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> GoodsOffRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .categoryId(goodsInfo.getCategoryId())
                        .categoryName(goodsInfo.getCategoryName())
                        .createTime(goodsInfo.getCreateTime())
                        .updateTime(goodsInfo.getUpdateTime())
                        .uid(goodsInfo.getUid())
                        .extra(goodsInfo.getExtra())
                        .build()
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp<PageRespDTO<GoodsApplyRespDTO>> getApplyGoods(GoodsApplyReqDTO condition) {
        Page<GoodsInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());

        Page<GoodsInfo> goodsInfoPage;
        // 申请页面
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
            goodsInfoPage = goodsInfoMapper.selectPage(page,
                    getQueryWrapperForStatusAndKeyword(null, 11, condition.getKeyword()));
        }
        // 申请不通过的页面
        else if ("2".equals(condition.getExtra())) {
            goodsInfoPage = goodsInfoMapper.selectPage(page,
                    getQueryWrapperForStatusAndKeyword(null, 20, condition.getKeyword()));
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        List<GoodsInfo> goodsInfos = goodsInfoPage.getRecords();
        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> GoodsApplyRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .categoryId(goodsInfo.getCategoryId())
                        .categoryName(goodsInfo.getCategoryName())
                        .createTime(goodsInfo.getCreateTime())
                        .updateTime(goodsInfo.getUpdateTime())
                        .uid(goodsInfo.getUid())
                        .extra(goodsInfo.getExtra())
                        .build()
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp<PageRespDTO<GoodsPlatformRespDTO>> getPlatformGoods(GoodsPlatformReqDTO condition) {
        Page<GoodsInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());

        Page<GoodsInfo> goodsInfoPage = null;
        QueryWrapper<GoodsInfo> queryWrapper = null;
        // 发布页面
        // 上架中
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 0, condition.getKeyword());
        } // 已成交
        else if ("2".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 1, condition.getKeyword());
        } // 成交中
        else if ("3".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 3, condition.getKeyword());
        }//  已取消
        else if ("4".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 4, condition.getKeyword());
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }
        // 平台发布
        queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_EXTRA, 1);
        goodsInfoPage = goodsInfoMapper.selectPage(page, queryWrapper);
        List<GoodsInfo> goodsInfos = goodsInfoPage.getRecords();
        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> GoodsPlatformRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .categoryId(goodsInfo.getCategoryId())
                        .categoryName(goodsInfo.getCategoryName())
                        .createTime(goodsInfo.getCreateTime())
                        .updateTime(goodsInfo.getUpdateTime())
                        .uid(goodsInfo.getUid())
                        .extra(goodsInfo.getExtra())
                        .build()
                ).collect(Collectors.toList())
        ));
    }


    // 根据status
    @Override
    public QueryWrapper<GoodsInfo> getQueryWrapperForStatus(Long goodsId, Integer status) {
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, status)
                .eq(DateBaseConstants.CommonColumnEnum.ID.getName(), goodsId);
        return queryWrapper;
    }

    // 根据keyWord
    @Override
    public QueryWrapper<GoodsInfo> getQueryWrapperForKeyword(Long goodsId, String keyword) {
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), goodsId);
        // 模糊匹配
        if (keyword != null && !"".equals(keyword)) {
            queryWrapper.like(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_TITLE, keyword);
        }
        return queryWrapper;
    }

    // 根据status和keyword
    @Override
    public QueryWrapper<GoodsInfo> getQueryWrapperForStatusAndKeyword(Long uid, Integer status, String keyword) {
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();

        // 当uid无效时，不加入uid的条件
        if (uid != null && uid != -1) {
            queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_UID, uid);
        }

        queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, status);

        // 模糊匹配
        if (keyword != null && !"".equals(keyword)) {
            queryWrapper.like(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_TITLE, keyword);
        }
        return queryWrapper;
    }


    @Override
    public RestResp<Void> deleteGoods(Long goodsId) {
        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 0);

        // 更新失败
        if (goodsInfoMapper.delete(queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }


    @Override
    public RestResp<Void> offShelfGoods(Long goodsId) {
        return offGoods(goodsId, false);
    }


    // flag表示是否为管理员操作
    public RestResp<Void> offGoods(Long goodsId, boolean flag) {
        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 0);
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        // 下架标记
        goodsInfo.setGoodsStatus(2);
        goodsInfo.setUpdateTime(LocalDateTime.now());
        if (flag) {
            goodsInfo.setExtra("2");
        }
        // 更新失败
        if (goodsInfoMapper.update(goodsInfo, queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> onShelfGoods(Long goodsId) {

        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 2);
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        // 上架标记
        goodsInfo.setGoodsStatus(0);
        goodsInfo.setUpdateTime(LocalDateTime.now());


        // 更新失败
        if (goodsInfoMapper.update(goodsInfo, queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateGoods(GoodsUpdateRespDTO dto) {

        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.CommonColumnEnum.ID.getName(), dto.getGoodsId());
        // status 为 0 或 2 的才能修改 0 代表上架 2 代表下架
        queryWrapper.and(wrapper -> wrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 0)
                .or().eq(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_STATUS, 2));

        GoodsInfo goodsInfo = goodsInfoMapper.selectById(dto.getGoodsId());
        // 修改信息
        goodsInfo.setGoodsTitle(dto.getGoodsTitle());
        goodsInfo.setGoodsContent(dto.getGoodsContent());
        goodsInfo.setPicUrl(dto.getPicUrl());
        goodsInfo.setOldDegree(dto.getOldDegree());
        goodsInfo.setBuyTime(dto.getBuyTime());
        goodsInfo.setGoodsPrice(dto.getPrice());
        goodsInfo.setCategoryId(dto.getCategoryId());
        goodsInfo.setCategoryName(dto.getCategoryName());
        goodsInfo.setUpdateTime(LocalDateTime.now());

        // 更新失败
        if (goodsInfoMapper.update(goodsInfo, queryWrapper) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }


    @Override
    public RestResp<Void> agreeApplyGoods(Long uid, Long goodsId) {
        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 11);
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        // 上架标记
        goodsInfo.setGoodsStatus(0);

        // 更新失败
        if (goodsInfoMapper.update(goodsInfo, queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> disagreeApplyGoods(Long uid, Long goodsId) {
        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 11);
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        // 上架标记
        goodsInfo.setGoodsStatus(20);

        // 更新失败
        if (goodsInfoMapper.update(goodsInfo, queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> platformOffGoods(Long uid, Long goodsId) {
        return offGoods(goodsId, false);
    }


    @Override
    public RestResp<PageRespDTO<AdminGoodsRespDTO>> getAllGoods(AdminGoodsReqDTO condition) {
        Page<GoodsInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());

        Page<GoodsInfo> goodsInfoPage = null;
        QueryWrapper<GoodsInfo> queryWrapper = null;


        // 发布页面
        // 上架中
        if (condition.getExtra() == null || "1".equals(condition.getExtra())
                || "".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 0, null);
        } // 已成交
        else if ("2".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 1, null);
        } // 成交中
        else if ("3".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 3, null);
        }//  已取消
        else if ("4".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 4, null);
        }// 下架的
        else if ("5".equals(condition.getExtra())) {
            queryWrapper = getQueryWrapperForStatusAndKeyword(null, 2, null);
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }

        // 模糊匹配
        if (condition.getKeyword() != null && !"".equals(condition.getKeyword())) {
            queryWrapper.and(wrapper ->
                    wrapper.like(DateBaseConstants.GoodsInfoTable.COLUMN_GOODS_TITLE, condition.getKeyword())
                            .or().like(DateBaseConstants.CommonColumnEnum.ID.getName(), condition.getKeyword()));
        }

        // 平台发布
        //queryWrapper.eq(DateBaseConstants.GoodsInfoTable.COLUMN_EXTRA, 1);
        goodsInfoPage = goodsInfoMapper.selectPage(page, queryWrapper);
        List<GoodsInfo> goodsInfos = goodsInfoPage.getRecords();
        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                goodsInfos.stream().map(goodsInfo -> AdminGoodsRespDTO.builder()
                        .goodsId(goodsInfo.getId())
                        .goodsContent(goodsInfo.getGoodsContent())
                        .goodsTitle(goodsInfo.getGoodsTitle())
                        .nickName(goodsInfo.getNickName())
                        .picUrl(goodsInfo.getPicUrl())
                        .price(goodsInfo.getGoodsPrice())
                        .buyTime(goodsInfo.getBuyTime())
                        .oldDegree(goodsInfo.getOldDegree())
                        .goodsStatus(goodsInfo.getGoodsStatus())
                        .categoryId(goodsInfo.getCategoryId())
                        .categoryName(goodsInfo.getCategoryName())
                        .createTime(goodsInfo.getCreateTime())
                        .updateTime(goodsInfo.getUpdateTime())
                        .uid(goodsInfo.getUid())
                        .extra(goodsInfo.getExtra())
                        .build()
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp adminOffGoods(Long uid, Long goodsId) {
        return offGoods(goodsId, false);
    }

    @Override
    public RestResp<Void> adminDeleteGoods(Long uid, Long goodsId) {
        QueryWrapper<GoodsInfo> queryWrapperForStatus = getQueryWrapperForStatus(goodsId, 0);
        // 更新失败
        if (goodsInfoMapper.delete(queryWrapperForStatus) == 0) {
            return RestResp.fail(CodeEnum.USER_REFRESH);
        }
        return RestResp.ok();
    }
}
