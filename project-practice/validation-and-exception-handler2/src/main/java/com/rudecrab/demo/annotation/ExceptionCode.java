package com.rudecrab.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RC
 * @description 自定义参数校验错误码和错误信息注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) // 表明该注解只能放在类的字段上
public @interface ExceptionCode {
    // 响应码code
    int value() default 10000;
    // 响应信息msg
    String message() default  "参数校验错误";
}
