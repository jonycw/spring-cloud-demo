package com.mint.consume.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
@Api(tags = "consume-消费服务接口")
public class ConsumeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/consume/testA")
    public String testA() {
        logger.info("消费服务访问testA");
        return "success testA";
    }

    @GetMapping("/consume/testB")
    public String testB() {
        logger.info("消费服务访问testB");
        return "success testB";
    }

    @RequestMapping("/testC")
    @SentinelResource(value = "testC",blockHandler = "blockHandlerHello")
    public String testC(String sourceName) {
        logger.info("消费服务访问testC");
        return "result:"+sourceName;
    }

    public String blockHandlerHello(BlockException e) {
        return "限流了";
    }


    @GetMapping("/testD")
    public String testD() {
        logger.info("消费服务访问testD");
        return "success testD";
    }


    @GetMapping("/testE")
    public String testE() {
        logger.info("消费服务访问testE");
        return "success testE";
    }

}
