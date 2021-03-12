package com.mint.other.result;


/**
 * @author ：cw
 * @date ：Created in 2020/5/22 下午3:27
 * @description：基础响应封装
 * @modified By：
 * @version: 0.0.1$
 */

public class ResultUtil {
    /**
     * @param @param  object
     * @param @return
     * @Title: success
     * @Description: 成功有返回数据
     */
    public static Result SUCCESS(Object object) {
        return new Result(ResultCode.SUCCESS,object);
    }

    /**
     * @return Result 200
     * @Title: success
     * @Description: 成功无返回数据
     */
    public static Result SUCCESS() {
        return new Result(ResultCode.SUCCESS);
    }


    public static Result OTHER(Boolean success,Integer code,String message) {
        return new Result(success,code,message);
    }

    public static Result OTHER(Boolean success,Integer code,String message,Object data) {
        return new Result(success,code,message,data);
    }

    public static Result OTHER(ResultCode resultCode,Object data) {
        return new Result(resultCode,data);
    }
    public static Result OTHER(ResultCode resultCode) {
        return new Result(resultCode.success(),resultCode.code(),resultCode.message());
    }

}
