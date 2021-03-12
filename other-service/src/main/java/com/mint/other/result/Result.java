package com.mint.other.result;


/**
 * @author ：cw
 * @date ：Created in 2020/5/22 下午3:40
 * @description：响应封装 范型类
 * @modified By：
 * @version: 0.0.1$
 */

public class Result<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    //不需要返回数据时使用
    public Result(ResultCode code){
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
    }
    //需要返回数据时
    public Result(ResultCode code, T data){
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        this.data = data;
    }
    public Result(Boolean success, Integer code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public Result(Boolean success, Integer code, String message,T data){
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
