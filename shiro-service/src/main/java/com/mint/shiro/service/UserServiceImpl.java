package com.mint.shiro.service;

import com.mint.shiro.model.po.PermissionsPo;
import com.mint.shiro.model.po.UsersPo;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Author: cw
 * @Date: 2021/1/6 14:06
 * @Description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public PermissionsPo selectUserRole(String username) {
        return PermissionsPo.builder()
                .permission("user:add")
                .build();
    }

    @Override
    public UsersPo selectUserNamePassword(String username) {
        return null;
    }
}

