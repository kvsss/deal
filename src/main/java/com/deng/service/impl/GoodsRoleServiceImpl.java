package com.deng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.core.auth.UserHolder;
import com.deng.dao.entity.UserRole;
import com.deng.dao.mapper.GoodsRoleMapper;
import com.deng.dao.mapper.UserRoleMapper;
import com.deng.service.GoodsRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsRoleServiceImpl implements GoodsRoleService {

    private final GoodsRoleMapper goodsRoleMapper;

    private final UserRoleMapper userRoleMapper;

    @Override
    public String getRoleByUid(Long id) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);

        UserRole userRole = userRoleMapper.selectOne(queryWrapper);
        if (userRole == null) {
            return "3";
        } else {
            return userRole.getRoleId().toString();
        }
    }

    @Override
    public UserRole getUserRoleByUid(Long id) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        return userRoleMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean isNormalUser() {
        String role = getRoleByUid(UserHolder.getUserId());
        return "3".equals(role);
    }
}
