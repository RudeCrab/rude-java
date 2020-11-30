package com.rudecrab.springsecurity.exception;

import com.rudecrab.springsecurity.enums.ResultCode;
import lombok.Getter;

/**
 * @author RudeCrab
 * @description 自定义异常
 */
@Getter
public class ApiException extends RuntimeException {
    private final String msg;
    private final ResultCode resultCode;

    public ApiException() {
        this(ResultCode.FAILED);
    }

    public ApiException(String msg) {
        this(ResultCode.FAILED, msg);
    }

    public ApiException(ResultCode resultCode) {
        this(resultCode, resultCode.getMsg());
    }

    public ApiException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
        this.msg =  msg;
    }
}
