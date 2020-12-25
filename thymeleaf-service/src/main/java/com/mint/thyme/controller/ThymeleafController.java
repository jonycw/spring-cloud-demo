package com.mint.thyme.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cw
 * @Date: 2020/12/25 14:12
 * @Description:
 */
@Controller
@Api(tags = "Thymeleaf服务接口")
@RefreshScope
@Slf4j
public class ThymeleafController {

    @ApiOperation("index页面显示")
    @GetMapping("/1")
    public String test(Model model) {

        log.info("Thymeleaf服务访问");

        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("msg","index");
        model.addAllAttributes(paramsMap);

        return "home/home";
    }

    @ApiOperation("index页面显示")
    @GetMapping("/2")
    public String test2(Model model) {

        log.info("Thymeleaf服务访问");

        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("msg","test");
        model.addAllAttributes(paramsMap);

        return "index";
    }
}

