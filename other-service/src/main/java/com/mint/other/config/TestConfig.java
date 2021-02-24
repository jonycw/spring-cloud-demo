package com.mint.other.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：cw
 * @date ：Created in 2020/7/17 上午10:35
 * @description：
 * @modified By：
 * @version: $
 */

@Component
@ConfigurationProperties(prefix = "temp")
@Data
public class TestConfig {
    private String temp1;
    private String temp2;
}
