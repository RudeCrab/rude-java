package com.rudecrab.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面视图控制器（渲染视图，而不是返回json数据）
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
