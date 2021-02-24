package com.mint.other.exception;

import com.mint.starter.common.Result;
import com.mint.starter.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
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
        log.error("系统发生错误: {}", e.getMessage());
        return ResultUtil.FAILED(e.getMessage());
    }


    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public Result userException(UserException e) {
        return ResultUtil.ERROR(false,e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = AdminException.class)
    @ResponseBody
    public Result adminException(AdminException e) {
        return ResultUtil.ERROR(false,e.getCode(), e.getMessage());
    }

}
