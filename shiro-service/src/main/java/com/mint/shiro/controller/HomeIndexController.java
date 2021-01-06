package com.mint.shiro.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cw
 * @Date: 2021/1/6 14:36
 * @Description:
 */

@Controller
@Api(tags = "shiro操作")
@Slf4j
public class HomeIndexController {


    //shiro 认证成功后默认跳转页面
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/403")
    public String err403(){
        return "403";
    }
    /**
     * 根据权限授权使用注解 @RequiresPermissions
     * */
    @GetMapping("/article")
    @RequiresPermissions("app:article:article")
    public String article(){
        return "article";
    }

    /**
     * 根据权限授权使用注解 @RequiresPermissions
     * */
    @GetMapping("/setting")
    @RequiresPermissions("app:setting:setting")
    public String setting(){
        return "setting";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Object loginsubmit(String username,String password) {
        Map<String, Object> map = new HashMap<>();
        log.info("登录请求入口:[username:{},password:{}]",username,password);
        //把身份 useName 和 证明 password 封装成对象 UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取当前的 subject
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            map.put("status", 0);
            map.put("message", "登录成功");
            return map;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            map.put("status", 1);
            map.put("message", "用户名或密码错误");
            return map;
        }
    }

    @ApiOperation("未经授权无法访问此页面")
    @GetMapping("/noauth")
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
}