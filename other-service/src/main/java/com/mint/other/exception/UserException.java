package com.mint.other.exception;

import com.mint.starter.common.ResultCode;
import lombok.Getter;

/**
 * @author ：cw
 * @date ：Created in 2020/8/6 上午9:31
 * @description：自定义用户异常
 * @modified By：
 * @version: $
 */
@Getter
public class UserException extends RuntimeException{

    private Integer code;

    public UserException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public UserException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
