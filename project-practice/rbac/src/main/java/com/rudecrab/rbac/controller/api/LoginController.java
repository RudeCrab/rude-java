package com.rudecrab.rbac.controller.api;

import com.rudecrab.rbac.model.param.LoginParam;
import com.rudecrab.rbac.model.vo.UserVO;
import com.rudecrab.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserVO login(@RequestBody @Validated LoginParam user) {
        return userService.login(user);
    }
}
