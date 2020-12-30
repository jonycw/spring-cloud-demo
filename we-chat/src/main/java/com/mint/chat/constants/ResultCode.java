package com.mint.chat.constants;

import lombok.Getter;

/**
 * @ClassName
 * @Author cw
 * @Description 枚举封装错误代码信息
 * @Date 2020-5-21
 */

@Getter
public enum ResultCode {

    SUCCESS(true,200,"操作成功~"),

    FAILED(true,-1,"系统繁忙，请稍后重试~"),

    PARAM_ERROR(false,-2, "参数不合法~");

    //操作是否成功
    private boolean success;
    //操作代码
    private int code;
    //提示信息
    private String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
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
