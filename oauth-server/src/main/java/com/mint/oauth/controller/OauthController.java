package com.mint.oauth.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:22
 * @description：
 * @modified By：
 * @version: $
 */

@RestController
@Api(tags = "oauth-授权服务接口")
@Slf4j
public class OauthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test/{id}")
    public String getProduct(@PathVariable String id) {
        //for debug
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("product:{}",id);
        return "product id : " + id;
    }

    @GetMapping("/login")
    public String getOrder() {


        return "order id : ";
    }


    @GetMapping("/hello/one//{id}")
    public String getHello1(@PathVariable String id) {
        //for debug
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("hello-one:{}",id);
        return "hello-one : " + id;
    }

    @GetMapping("/hello/two/{id}")
    public String getHello2(@PathVariable String id) {
        //for debug
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("hello-two:{}",id);
        return "hello-two : " + id;
    }
}
