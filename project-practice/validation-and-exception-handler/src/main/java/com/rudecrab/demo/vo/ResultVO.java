package com.rudecrab.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author RudeCrab
 * @description 自定义统一响应体
 */
@Getter
@ApiModel
public class ResultVO<T> {
    @ApiModelProperty(value = "状态码", notes = "默认1000是成功")
    private int code;
    @ApiModelProperty(value = "响应信息", notes = "来说明响应情况")
    private String msg;
    @ApiModelProperty(value = "响应的具体数据")
    private T data;

    public ResultVO(T data) {
        this(1000, "success", data);
    }

    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
