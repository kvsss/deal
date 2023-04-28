package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.UserInfoUptReqDTO;
import com.deng.dto.req.UserLoginReqDTO;
import com.deng.dto.req.UserRegisterReqDTO;
import com.deng.dto.resp.UserInfoRespDTO;
import com.deng.dto.resp.UserLoginRespDTO;
import com.deng.dto.resp.UserRegisterRespDTO;

/**
 * @author :deng
 * @version :1.0
 * @description :用户模块
 * @since :1.8
 */
public interface UserService {
    /**
     *注册
     * @param dto
     * @return
     */
    RestResp<UserRegisterRespDTO> register(UserRegisterReqDTO dto);

    /**
     *登录
     * @param dto
     * @return
     */
    RestResp<UserLoginRespDTO> login(UserLoginReqDTO dto);

    /**
     * 获得用户信息
     * @param uid 用户id
     * @return
     */
    RestResp<UserInfoRespDTO> getUserInfo(Long uid);

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    RestResp<Void> updateUserInfo(UserInfoUptReqDTO dto);

    /**
     * 删除用户
     * @param uid
     * @return
     */
    RestResp<Void> deleteUser(Long uid);
}
