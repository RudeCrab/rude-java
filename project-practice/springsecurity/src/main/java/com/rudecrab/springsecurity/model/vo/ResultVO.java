package com.rudecrab.springsecurity.model.vo;

import com.rudecrab.springsecurity.enums.ResultCode;
import lombok.Getter;

/**
 * 自定义统一响应体, 相关用法可以参考文章：
 * https://mp.weixin.qq.com/s?__biz=MzkzMjE3NTA3Mg==&mid=2247484586&idx=1&sn=88f39689a53a024d2a3cd1d6101ebd60
 *
 * @author RudeCrab
 */
@Getter
public class ResultVO<T> {
    /**
     * 状态码, 默认1000是成功
     */
    private int code;
    /**
     * 响应信息, 来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", code, msg, data.toString());
    }
}
