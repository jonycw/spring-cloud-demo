package com.mint.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：cw
 * @date ：Created in 2020/6/12 下午2:35
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);// 设置超时
        requestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
