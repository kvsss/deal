package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.DateBaseConstants;
import com.deng.core.constant.SystemConfigConstants;
import com.deng.core.util.JwtUtils;
import com.deng.dao.entity.UserInfo;
import com.deng.dao.mapper.UserInfoMapper;
import com.deng.dto.req.UserLoginReqDTO;
import com.deng.dto.req.UserRegisterReqDTO;
import com.deng.dto.resp.UserLoginRespDTO;
import com.deng.dto.resp.UserRegisterRespDTO;
import com.deng.manage.redis.VerifyCodeManager;
import com.deng.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

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
                        .token(JwtUtils.generateToken(userInfo.getId(), SystemConfigConstants.DEAL_FRONT_KEY))
                        .build()
        );
    }
}
