package com.mint.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:10
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderApplication {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        public static void main(String[] args) {
            SpringApplication.run(OrderApplication.class, args);
        }
        public void run(String... args) throws Exception {
            logger.info("-------------- 订单服务 启动成功 --------------");
        }
}
