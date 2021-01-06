package com.mint.shiro.service;

import com.mint.shiro.model.po.PermissionsPo;
import com.mint.shiro.model.po.UsersPo;

/**
 * @Author: cw
 * @Date: 2021/1/6 14:06
 * @Description:
 */
public interface UserService {

    PermissionsPo selectUserRole(String username);

    UsersPo selectUserNamePassword(String username);

}

