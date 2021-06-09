package com.example.opencvdemo.exception;


import com.example.opencvdemo.result.CommonResult;
import com.example.opencvdemo.result.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aaron
 * @since 2020-10-10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理空指针的异常
     * @param req request
     * @param e exception
     * @return CommonResult
     */
    @ExceptionHandler(value =NullPointerException.class)
    public CommonResult<Boolean> exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error(req.getServletPath()+"接口发生异常！原因是:{}",e);
        return CommonResult.failed(ErrorCode.B0001,"空指针的异常");
    }

    /**
     * 异常
     * @param req request
     * @param e exception
     * @return CommonResult
     */
    @ExceptionHandler(value =Exception.class)
    public CommonResult<Boolean> exceptionHandler(HttpServletRequest req, Exception e){
        log.error(req.getServletPath()+"接口发生异常！原因是:{}",e);
        return CommonResult.failed(ErrorCode.B0001,e.getMessage());
    }

    /**
     * 自定义异常
     * @param req request
     * @param e   exception
     * @return CommonResult
     */
    @ExceptionHandler(value = PublicException.class)
    public CommonResult<Boolean> exceptionHandler(HttpServletRequest req, PublicException e) {
        log.error(req.getServletPath()+"接口发生异常！原因是:{}",e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

}
