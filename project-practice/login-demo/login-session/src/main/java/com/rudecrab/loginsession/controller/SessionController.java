package com.rudecrab.loginsession.controller;

import com.rudecrab.loginsession.entity.User;
import com.rudecrab.loginsession.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author RudeCrab
 */
@RestController
public class SessionController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(@RequestBody User user, HttpSession session) {
        if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())) {
            session.setAttribute("user", user);
            return "登录成功";
        }
        return "账号或密码错误";
    }

    @GetMapping("api")
    public String api() {
        // 各种业务操作
        userService.doSomething();
        return "api成功返回数据";
    }

    @GetMapping("api2")
    public String api2() {
        // 各种业务操作
        userService.doSomething();
        return "api2成功返回数据";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "退出成功";
    }
}
