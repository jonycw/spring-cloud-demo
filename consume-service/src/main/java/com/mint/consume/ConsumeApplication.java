package com.mint.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:18
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumeApplication {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ConsumeApplication.class, args);
    }
    public void run(String... args) throws Exception {
        logger.info("-------------- 消费服务 启动成功 --------------");
    }

}
