package com.mint.shiro.controller;

import com.mint.starter.common.ResultCode;
import com.mint.starter.common.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
@RefreshScope
@Validated
public class HomeIndexController {

    @Value("${spring.datasource.username}")
    private String usernameDB;

    @Value("${spring.datasource.password}")
    private String passwordDB;

    //shiro 认证成功后默认跳转页面
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/403")
    public String err403() {
        return "403";
    }

    @GetMapping("/article")
    @RequiresPermissions("app:article:article")
    public String article() {
        return "article";
    }

    @GetMapping("/setting")
    @RequiresPermissions("app:setting:setting")
    public String setting() {
        return "setting";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login.jsp")
    public String loginJsp() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Object loginsubmit(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        log.info("动态数据源信息:[usernameDB:{},passwordDB:{}]",usernameDB,passwordDB);
        log.info("登录请求入口:[username:{},password:{}]", username, password);
        //把身份 useName 和 证明 password 封装成对象 UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取当前的 subject

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResultUtil.SUCCESS();
        } catch (IncorrectCredentialsException e) {
            log.error("账号或者密码输入有误~~");
            e.printStackTrace();
            return ResultUtil.ERROR(ResultCode.ACCOUNT_NOT_CORRECT);
        } catch (AuthenticationException e) {
            log.error("账号或者密码输入错误~~");
            e.printStackTrace();
            return ResultUtil.ERROR(ResultCode.ACCOUNT_NOT_CORRECT);
        }
    }

    @ApiOperation("未经授权无法访问此页面")
    @GetMapping("/noauth")
    public String unauthorized() {
        return "setting";
    }
}