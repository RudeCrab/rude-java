package com.rudecrab.springsecurity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解，用于标识需要权限处理的接口
 *
 * @author RudeCrab
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auth {
    /**
     * 权限id，模块id + 方法id需要唯一
     */
    long id();
    /**
     * 权限名称
     */
    String name();
}
