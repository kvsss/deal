package com.deng.service;

import com.deng.dao.entity.UserRole;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public interface GoodsRoleService {

    String getRoleByUid(Long id);

    UserRole getUserRoleByUid(Long id);

}
