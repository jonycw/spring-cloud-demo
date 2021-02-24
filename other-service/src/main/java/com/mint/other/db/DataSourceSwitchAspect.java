package com.mint.other.db;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ：cw
 * @date ：Created in 2020/6/10 下午12:12
 * @description：
 * @modified By：
 * @version: $
 */
@Component
@Aspect
@Order(1) //这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
@Slf4j
public class DataSourceSwitchAspect {
    @Pointcut("@annotation(DBMapperConvert)")
    private void dbChoose() {
    }
    @Before("dbChoose()")
    public void convertDb(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DBMapperConvert annotation = method.getAnnotation(DBMapperConvert.class);
        String value = annotation.value();
        if(DBTypeEnum.DATASOURCE_ONE.Value().equals(value)){
            log.info("---------切换到 AUTUMN_APPLY 数据源开始操作...:"+value);
            DbContextHolder.setDbType(DBTypeEnum.DATASOURCE_ONE);
        }else {
            log.info("---------切换到 AUTUMN_POINT 数据源开始操作...:"+value);
            DbContextHolder.setDbType(DBTypeEnum.DATASOURCE_TWO);
        }

    }
    @After("dbChoose()")
    public void afterPointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DBMapperConvert annotation = method.getAnnotation(DBMapperConvert.class);
        String value = annotation.value();
        log.info("---------切换到 "+value+" 数据源操作结束...:"+value);
        DbContextHolder.clearDbType();
    }
}
