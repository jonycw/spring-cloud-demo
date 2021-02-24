package com.mint.starter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName
 * @Author cw
 * @Description 枚举封装错误代码信息
 * @Date 2020-5-21
 */

@Getter
@AllArgsConstructor
public enum ResultCode {

    UN_PERMISSION(false,10,"没有权限访问~"),
    ACCOUNT_NOT_CORRECT(false,11,"账号或者密码输入有误~"),

    // 全局信息
    SUCCESS(true,200,"操作成功"),
    FAILED(true,-1,"系统繁忙，请稍后重试~"),
    PARAM_ERROR(false,201, "参数不合法");


    //操作是否成功
    final boolean success;
    //操作代码
    final int code;
    //提示信息
    final String message;

    public boolean success(){
        return success;
    }
    public int code(){
        return code;
    }
    public String message(){
        return message;
    }

}
