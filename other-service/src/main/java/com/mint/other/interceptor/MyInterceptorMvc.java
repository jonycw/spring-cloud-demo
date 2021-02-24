package com.mint.other.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cw
 */
@Configuration
public class MyInterceptorMvc implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    public MyInterceptorMvc(LoginInterceptor loginInterceptor, AdminLoginInterceptor adminLoginInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.adminLoginInterceptor = adminLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 定义路由拦截数组
        String[] userPathPattern = {"/apply/**"};
        String[] userExcludePathPatterns = {"/apply/test/**"};
        registry.addInterceptor(loginInterceptor).addPathPatterns(userPathPattern).excludePathPatterns(userExcludePathPatterns);

        // 管理员拦截
        String[] adminPathPattern = {"/admin/**","/analyse/**"};
        String[] adminExcludePathPatterns = {"/admin/login/**","/admin/goodsInfoById/**"};

        registry.addInterceptor(adminLoginInterceptor).addPathPatterns(adminPathPattern).excludePathPatterns(adminExcludePathPatterns);
    }


    /**
     * 跨域处理
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = {"http://xxx.xx.xxx/"};
        registry.addMapping("/**").allowCredentials(true).allowedHeaders("*").allowedMethods("*").allowedOrigins(origins);
    }
}
