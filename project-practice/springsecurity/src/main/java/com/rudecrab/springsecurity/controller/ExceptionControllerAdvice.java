package com.rudecrab.springsecurity.controller;

import com.rudecrab.springsecurity.enums.ResultCode;
import com.rudecrab.springsecurity.exception.ApiException;
import com.rudecrab.springsecurity.model.vo.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理，相关用法可以参考文章：
 * https://mp.weixin.qq.com/s?__biz=MzkzMjE3NTA3Mg==&mid=2247484586&idx=1&sn=88f39689a53a024d2a3cd1d6101ebd60
 *
 * @author RudeCrab
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResultVO<String> apiExceptionHandler(ApiException e) {
        return new ResultVO<>(e.getResultCode(), e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

}
