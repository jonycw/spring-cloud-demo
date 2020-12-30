package com.mint.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: cw
 * @Date: 2020/12/30 11:30
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "we-chat")
@Data
public class WeChatProperties {

    private String publicAppId;
    private String publicAppSecret;


    private String publicAccessToken;


    private String publicPageBaseOauth;
    private String publicPageAccessToken;
    private String publicPageUserInfo;

}

