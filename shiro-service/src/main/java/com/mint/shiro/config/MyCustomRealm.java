package com.mint.shiro.config;

import com.mint.shiro.model.po.PermissionsPo;
import com.mint.shiro.model.po.UsersPo;
import com.mint.shiro.service.UserService;
import com.mint.shiro.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: cw
 * @Date: 2021/1/6 13:50
 * @Description:
 */

@Slf4j
public class MyCustomRealm extends AuthorizingRealm {


    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("授权方法开始:[current-username:{}]", SecurityUtils.getSubject().getPrincipal());
        // 获取当前登录的这个对象
        UsersPo usersPo = (UsersPo) SecurityUtils.getSubject().getPrincipal();
        Long userId= usersPo.getId();//转成 user 对象
        // 授权 新建一个授权模块 SimpleAuthorizationInfo 把 权限赋值给当前的用户
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置当前会话拥有的角色 实际场景根据业务来如从数据库获取角色列表
        Set<String> roles=new HashSet<>();
        roles.add("admin");
        roles.add("finance");
        info.setRoles(roles);

        //设置当前会话可以拥有的权限 实际场景根据业务来如从数据库获取角色列表下的权限列表
        Set<String> permissions=new HashSet<>();
        permissions.add("app:setting:setting");
        permissions.add("app:article:article");
        info.setStringPermissions(permissions);
        log.info("授权方法结束:[current-username:{}]", SecurityUtils.getSubject().getPrincipal());

        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        log.info("认证方法开始:[current-username:{},current-password:{},password:{}]",authenticationToken.getPrincipal(),authenticationToken.getCredentials(),password);
        //根据用户名从数据库获取密码
//        UsersPo userNamePassword = userService.selectUserNamePassword(userName);
//        String password = userNamePassword.getPassword();
//        String salt = userNamePassword.getSalt();
        //对身份+证明的数据认证 这里模拟了一个数据源
        //如果是数据库 那么这里应该调用数据库判断用户名密码是否正确
        if (!"admin".equals(username) || !"123456".equals(password)) {
            log.info("账号或密码不正确");
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        //认证通过
        UsersPo user = new UsersPo();
        user.setId(1L);//假设用户ID=1
        user.setUsername(username);
        user.setPassword(password);
        //建立一个 SimpleAuthenticationInfo 认证模块，包括了身份】证明等信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password,getName());
        log.info("认证方法结束");
        return info;

    }
}

