package com.mint.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: cw
 * @Date: 2020/12/30 11:23
 * @Description:
 */

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class WeChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class,args);
    }

    public void run(String... args) throws Exception {
        log.info("-------------- 微信服务 启动成功 --------------");
    }
}

