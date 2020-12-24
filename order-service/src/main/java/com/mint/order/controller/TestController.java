package com.mint.order.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:22
 * @description：
 * @modified By：
 * @version: $
 */

@RestController
@Api(tags = "order-订单服务接口")
@RefreshScope
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("文章列表")
    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        logger.info("订单服务访问");
        return a + b;
    }

    @ApiOperation("文章列表1")
    @PostMapping("/oauth/token")
    public JSONObject oauth(String username,String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa","aaaa");
        logger.info(jsonObject.toJSONString());
        return jsonObject;
    }
}
