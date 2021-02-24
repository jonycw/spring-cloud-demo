package com.mint.other.interceptor;


import com.mint.other.util.CookieUtil;
import com.mint.other.util.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cw
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;

    @Autowired
    public LoginInterceptor(RedisTemplate redisTemplate) {
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
        Cookie[] cookies = request.getCookies();

        // 判断 cookies 是否存在
        if (cookies == null || cookies.length == 0) {
            // 抛出自定义异常捕获
        }

        // 判断 传递过来的cookie中 token 是否存在
        String keyValue = CookieUtil.getCookie(cookies, "best_stu");
        if (keyValue == null) {
            // 抛出自定义异常捕获
        }

        // 将业务信息加入到本地线程中
        MyThreadLocal.set(new Object());

        return true;
    }
    /**
     * 拦截结束后
     * @param request request
     * @param response response
     * @param handler handler
     * @param ex ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        MyThreadLocal.remove();
    }

}
