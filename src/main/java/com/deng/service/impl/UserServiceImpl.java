package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.core.constant.SystemConfigConstants;
import com.deng.core.util.JwtUtils;
import com.deng.dao.entity.GoodsRole;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.entity.UserRole;
import com.deng.dao.mapper.GoodsCategoryMapper;
import com.deng.dao.mapper.GoodsRoleMapper;
import com.deng.dao.mapper.UserInfoMapper;
import com.deng.dao.mapper.UserRoleMapper;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;
import com.deng.manage.redis.VerifyCodeManager;
import com.deng.service.GoodsRoleService;
import com.deng.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;

    private final VerifyCodeManager verifyCodeManager;

    private final GoodsCategoryMapper goodsCategoryMapper;

    private final GoodsRoleService goodsRoleService;

    private final UserRoleMapper userRoleMapper;


    private final JwtUtils JwtUtils;

    @Override
    public RestResp<UserRegisterRespDTO> register(UserRegisterReqDTO dto) {
        // 验证码校验
        if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())) {
            // 这里可以推断泛型
            return RestResp.fail(CodeEnum.USER_VERIFY_CODE_ERROR);
        }

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.UserInfoTable.COLUMN_USERNAME, dto.getUsername())
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_1.getLimitSql());

        if (userInfoMapper.selectCount(queryWrapper) > 0) {
            // 手机号已注册
            return RestResp.fail(CodeEnum.USER_NAME_EXIST);
        }

        // 注册成功,保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName(dto.getUsername());
        // 64位随机salt
        userInfo.setSalt(RandomStringUtils.randomAlphabetic(64));
        userInfo.setPassword(
                DigestUtils.md5DigestAsHex(
                        (dto.getPassword() + userInfo.getSalt())
                                .getBytes(StandardCharsets.UTF_8)));
        userInfo.setStatus(0);
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());

        userInfoMapper.insert(userInfo);
        return RestResp.ok(
                UserRegisterRespDTO.builder()
                        .uid(userInfo.getId())
                        .token(JwtUtils.generateToken(userInfo.getId(), SystemConfigConstants.DEAL_FRONT_KEY))
                        .build()
        );
    }

    @Override
    public RestResp<UserLoginRespDTO> login(UserLoginReqDTO dto) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.UserInfoTable.COLUMN_USERNAME, dto.getUsername())
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_1.getLimitSql());

        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            return RestResp.fail(CodeEnum.USER_NOT_EXIST);
        }
        if (!Objects.equals(userInfo.getPassword(),
                DigestUtils.md5DigestAsHex(
                        (dto.getPassword() + userInfo.getSalt())
                                .getBytes(StandardCharsets.UTF_8)))) {
            // 密码错误
            return RestResp.fail(CodeEnum.USER_PASSWORD_ERROR);
        }

        //登录成功,生成jWT,并返回
        return RestResp.ok(
                UserLoginRespDTO.builder()
                        .uid(userInfo.getId())
                        .nickName(userInfo.getNickName())
                        .role(goodsRoleService.getRoleByUid(userInfo.getId()))
                        .token(JwtUtils.generateToken(userInfo.getId(), SystemConfigConstants.DEAL_FRONT_KEY))
                        .build()
        );
    }

    @Override
    public RestResp<UserInfoRespDTO> getUserInfo(Long uid) {
        UserInfo userInfo = userInfoMapper.selectById(uid);
        return RestResp.ok(UserInfoRespDTO.builder()
                .nickName(userInfo.getNickName())
                .userSex(userInfo.getUserSex())
                .userPhoto(userInfo.getUserPhoto())
                .build()
        );
    }

    @Override
    public RestResp<Void> updateUserInfo(UserInfoUptReqDTO dto) {
        // 扩展点:如果图片存在,考虑删除
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dto.getUid());
        // nickName不能为null
        if (dto.getNickName() != null && !dto.getNickName().isEmpty()) {
            userInfo.setNickName(dto.getNickName());
        }
        // userPhoto不能为null
        if (dto.getUserPhoto() != null && !dto.getUserPhoto().isEmpty()) {
            userInfo.setUserPhoto(dto.getUserPhoto());
        }
        // userSex不能为null
        if (dto.getUserSex() != null &&
                (dto.getUserSex() == 0 || dto.getUserSex() == 1)) {
            userInfo.setUserSex(dto.getUserSex());
        }
        userInfoMapper.updateById(userInfo);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteUser(Long uid) {
        userInfoMapper.deleteById(uid);
        return RestResp.ok();
    }

    @Override
    public RestResp<PageRespDTO<AdminUserRespDTO>> getAllUserInfo(AdminUserInfoReqDTO condition) {
        Page<UserInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        if (condition.getKeyword() == null || condition.getKeyword().isEmpty()) {
            queryWrapper = null;
        } else {
         /*   queryWrapper.and(wrapper -> wrapper.like(DateBaseConstants.UserInfoTable.COLUMN_USERNAME, condition.getKeyword())
                    .or()
                    .like(DateBaseConstants.UserInfoTable.COLUMN_NICK_NAME, condition.getKeyword()));*/
            queryWrapper.like(DateBaseConstants.UserInfoTable.COLUMN_USERNAME, condition.getKeyword())
                    .or()
                    .like(DateBaseConstants.UserInfoTable.COLUMN_NICK_NAME, condition.getKeyword());
        }

        IPage<UserInfo> result = userInfoMapper.selectPage(page, queryWrapper);
        List<UserInfo> records = result.getRecords();

        return RestResp.ok(PageRespDTO.of(condition.getPageNum(), condition.getPageSize(), page.getTotal(),
                records.stream().map(
                        userInfo -> {
                            goodsRoleService.getRoleByUid(userInfo.getId());
                            return AdminUserRespDTO.builder()
                                    .uid(userInfo.getId())
                                    .userName(userInfo.getUsername())
                                    .nickName(userInfo.getNickName())
                                    .userSex(userInfo.getUserSex())
                                    .userPhoto(userInfo.getUserPhoto())
                                    .status(userInfo.getStatus())
                                    .role(goodsRoleService.getRoleByUid(userInfo.getId()))
                                    .build();
                        }
                ).collect(Collectors.toList())
        ));
    }


    @Override
    public RestResp<Void> adminDisableUser(Long uid, Long userId) {

        if ("1".equals(goodsRoleService.getRoleByUid(userId))) {
            // 该用户为管理员
            return RestResp.fail("没有权限!");
        }

        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (userInfo.getStatus() == 0) {
            userInfo.setStatus(1);
        } else if (userInfo.getStatus() == 1) {
            userInfo.setStatus(0);
        } else {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }
        if (userInfoMapper.updateById(userInfo) == 0) {
            return RestResp.fail(CodeEnum.SYSTEM_ERROR);
        }
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> adminOpenUser(Long uid, Long userId) {
        String role = goodsRoleService.getRoleByUid(userId);

        if (role == null || role.isEmpty() || "3".equals(role)) {
            // 设置为平台角色
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(2L);
            userRoleMapper.insert(userRole);
        } else if ("2".equals(role)) {
            // 设置为平台角色
            UserRole userRole = goodsRoleService.getUserRoleByUid(userId);
            userRoleMapper.deleteById(userRole.getId());
        } else {
            return RestResp.fail("没有权限!");
        }
        return RestResp.ok();
    }


    @Override
    public RestResp<Void> adminDeleteUser(Long uid, Long userId) {
        if ("1".equals(goodsRoleService.getRoleByUid(userId))) {
            // 该用户为管理员
            return RestResp.fail("没有权限!");
        }
        if (userInfoMapper.deleteById(userId) == 0) {
            return RestResp.fail("删除失败!");
        }
        return RestResp.ok();
    }


    @Override
    public RestResp<UserLoginRespDTO> adminLogin(UserLoginReqDTO dto) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DateBaseConstants.UserInfoTable.COLUMN_USERNAME, dto.getUsername())
                .last(DateBaseConstants.LimitSQLtEnum.LIMIT_1.getLimitSql());

        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            return RestResp.fail(CodeEnum.USER_NOT_EXIST);
        }
        if (!Objects.equals(userInfo.getPassword(),
                DigestUtils.md5DigestAsHex(
                        (dto.getPassword() + userInfo.getSalt())
                                .getBytes(StandardCharsets.UTF_8)))) {
            // 密码错误
            return RestResp.fail(CodeEnum.USER_PASSWORD_ERROR);
        }
        if (!"1".equals(goodsRoleService.getRoleByUid(userInfo.getId()))) {
            return RestResp.fail(CodeEnum.USER_NOT_EXIST);
        }

        //登录成功,生成jWT,并返回
        return RestResp.ok(
                UserLoginRespDTO.builder()
                        .uid(userInfo.getId())
                        .nickName(userInfo.getNickName())
                        .role(goodsRoleService.getRoleByUid(userInfo.getId()))
                        .token(JwtUtils.generateToken(userInfo.getId(), SystemConfigConstants.DEAL_FRONT_KEY))
                        .build()
        );
    }
}
