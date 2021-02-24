package com.mint.other.util;

import javax.servlet.http.Cookie;

/**
 * @Author cw
 * @Date 2020/6/8 17:22
 * @Description:
 */
public class CookieUtil {
    /**
     * 设置cookie的值
     * @param key key
     * @param value value
     * @param expireTime time
     * @return Cookie
     */
    public static Cookie setCookie(String key, String value, Integer expireTime) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(expireTime);
        return cookie;
    }

    /**
     * 获取某个key的cookie的值
     * @param cookies cookies
     * @param key key
     * @return object
     */
    public static String getCookie(Cookie[] cookies, String key) {
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
