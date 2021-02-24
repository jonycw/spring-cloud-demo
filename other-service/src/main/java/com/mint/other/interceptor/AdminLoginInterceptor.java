package com.mint.other.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cw
 */
@Slf4j
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;

    @Autowired
    public AdminLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 学员登录拦截器
     * @param request 请求
     * @param response 返回response
     * @param handler 处理
     * @return boolean 返回
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

}
