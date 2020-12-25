package com.mint.thyme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: cw
 * @Date: 2020/12/25 13:59
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ThymeleafApplication {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }
    public void run(String... args) throws Exception {
        logger.info("-------------- Thymeleaf服务 启动成功 --------------");
    }
}

