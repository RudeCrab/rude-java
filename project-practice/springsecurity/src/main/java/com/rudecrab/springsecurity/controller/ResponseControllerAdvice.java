package com.rudecrab.springsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudecrab.springsecurity.exception.ApiException;
import com.rudecrab.springsecurity.model.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局统一响应，相关用法可以参考文章：
 * https://mp.weixin.qq.com/s?__biz=MzkzMjE3NTA3Mg==&mid=2247484586&idx=1&sn=88f39689a53a024d2a3cd1d6101ebd60
 *
 * @author RudeCrab
 */
@RestControllerAdvice(basePackages = {"com.rudecrab.springsecurity.controller.api"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !returnType.getParameterType().equals(ResultVO.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResultVO<>(data));
            } catch (JsonProcessingException e) {
                throw new ApiException("返回String类型错误");
            }
        }
        // 将原本的数据包装在ResultVO里
        return new ResultVO<>(data);
    }
}
