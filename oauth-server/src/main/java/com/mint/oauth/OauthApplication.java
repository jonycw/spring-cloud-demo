package com.mint.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：cw
 * @date ：Created in 2020/8/3 下午5:17
 * @description：
 * @modified By：
 * @version: $
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OauthApplication {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }
    public void run(String... args) throws Exception {
        logger.info("-------------- 授权服务 启动成功 --------------");
    }
}
