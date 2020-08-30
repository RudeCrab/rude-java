package com.rudecrab.loginjwt.controller;

import com.rudecrab.loginjwt.entity.User;
import com.rudecrab.loginjwt.service.UserService;
import com.rudecrab.loginjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RudeCrab
 */
@RestController
public class JwtController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // 判断账号密码是否正确，这一步肯定是要读取数据库中的数据来进行校验的，这里为了模拟就省去了
        if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())) {
            // 如果正确的话就返回生成的token
            return JwtUtil.generate(user.getUsername());
        }
        return "账号密码错误";
    }

    @GetMapping("api")
    public String api() {
        userService.doSomething();
        return "api成功返回数据";
    }

    @GetMapping("api2")
    public String api2() {
        userService.doSomething();
        return "api2成功返回数据";
    }
}
