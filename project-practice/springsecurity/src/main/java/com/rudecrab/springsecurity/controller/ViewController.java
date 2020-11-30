package com.rudecrab.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面视图控制器（渲染视图，而不是返回json数据）
 * 虽然该项目可以直接访问前端页面，但是所有接口都是前后端分离模式，页面是我为了方便大家看到效果就整合到项目中的
 *
 * @author RudeCrab
 */
@Controller
public class ViewController {
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        return "index";
    }
}
