package com.mint.shiro.exception;

import com.mint.starter.common.Result;
import com.mint.starter.common.ResultCode;
import com.mint.starter.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：cw
 * @date ：Created in 2020/8/6 上午9:30
 * @description：全剧异常处理
 * @modified By：
 * @version: $
 */

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.error("系统全局发生错误:[出错原因:{},出错信息:{}]",e.getCause(),e.getMessage());
        if (e instanceof AuthorizationException){
           return ResultUtil.ERROR(ResultCode.UN_PERMISSION);
        }
        if (e instanceof IncorrectCredentialsException){
            return ResultUtil.ERROR(ResultCode.ACCOUNT_NOT_CORRECT);
        }
        return ResultUtil.ERROR(ResultCode.UN_PERMISSION);
    }

    @ExceptionHandler(value = AdminException.class)
    @ResponseBody
    public Result adminException(AdminException e) {
        return ResultUtil.ERROR(false,e.getCode(), e.getMessage());
    }

}
