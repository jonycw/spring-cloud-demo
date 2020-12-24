package com.mint.oauth.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(tags = "auth-授权服务接口")
@RequestMapping("/cloud-auth")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        logger.info("auth服务访问");
        return a + b;
    }
}
