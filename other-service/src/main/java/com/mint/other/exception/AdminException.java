package com.mint.other.exception;


import com.mint.starter.common.ResultCode;
import lombok.Getter;

/**
 * @author ：cw
 * @date ：Created in 2020/8/10 下午4:47
 * @description：
 * @modified By：
 * @version: $
 */

@Getter
public class AdminException extends RuntimeException{

    private Integer code;

    public AdminException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public AdminException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
