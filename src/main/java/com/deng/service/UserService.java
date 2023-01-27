package com.deng.service;

import com.deng.core.common.resp.RestResp;
import com.deng.dto.req.UserLoginReqDTO;
import com.deng.dto.req.UserRegisterReqDTO;
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

}
