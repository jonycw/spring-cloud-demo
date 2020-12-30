package com.mint.chat.constants;


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
     * @return Result 199
     * @Title: success
     * @Description: 成功无返回数据
     */
    public static Result FAILED(Object object) {
        return new Result(ResultCode.FAILED,object);
    }
    /**
     * @return Result 199
     * @Title: success
     * @Description: 成功无返回数据
     */
    public static Result FAILED() {
        return new Result(ResultCode.FAILED);
    }
    /**
     * @return Result 200
     * @Title: success
     * @Description: 成功无返回数据
     */
    public static Result SUCCESS() {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     *
     * @param resultCode
     * @return
     */
    public static Result ERROR(ResultCode resultCode) {
        return new Result(resultCode.success(),resultCode.code(),resultCode.message());
    }

    public static Result ERROR(ResultCode resultCode,Object data) {
        return new Result(resultCode,data);
    }

    public static Result ERROR(Boolean success,Integer code,String message) {
        return new Result(success,code,message);
    }

    public static Result OTHER(ResultCode resultCode,Object data) {
        return new Result(resultCode,data);
    }
    public static Result OTHER(ResultCode resultCode) {
        return new Result(resultCode.success(),resultCode.code(),resultCode.message());
    }

}
