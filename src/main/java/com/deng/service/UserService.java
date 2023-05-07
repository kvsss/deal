package com.deng.service;

import com.deng.core.common.resp.PageRespDTO;
import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.*;
import com.deng.dto.resp.*;

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

    /**
     * 获取所有用户信息
     * @param condition
     * @return
     */
    RestResp<PageRespDTO<AdminUserRespDTO>> getAllUserInfo(AdminUserInfoReqDTO condition);

    /**
     * 管理员禁用用户
     * @param uid
     * @param userId
     * @return
     */
    RestResp<Void> adminDisableUser(Long uid, Long userId);


    /**
     * 管理员开启用户
     * @param uid
     * @param userId
     * @return
     */
    RestResp<Void> adminOpenUser(Long uid, Long userId);

    /**
     * 管理员删除用户
     * @param uid
     * @param userId
     * @return
     */
    RestResp<Void> adminDeleteUser(Long uid, Long userId);

    /**
     * 管理员登录
     * @param dto
     * @return
     */
    RestResp<UserLoginRespDTO> adminLogin(UserLoginReqDTO dto);
}
