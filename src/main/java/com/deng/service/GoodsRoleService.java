package com.deng.service;

import com.deng.dao.entity.UserRole;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface GoodsRoleService {

    /**
     * 根据用户id获取角色
     * @param id
     * @return
     */
    String getRoleByUid(Long id);

    /**
     * 根据用户id获取UserRole
     * @param id
     * @return
     */
    UserRole getUserRoleByUid(Long id);


    /**
     * 是否为普通用户
     * @return
     */
    boolean isNormalUser();

}
