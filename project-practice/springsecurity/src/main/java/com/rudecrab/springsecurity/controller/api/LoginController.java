package com.rudecrab.springsecurity.controller.api;

import com.rudecrab.springsecurity.model.param.LoginParam;
import com.rudecrab.springsecurity.model.vo.UserVO;
import com.rudecrab.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * @author RudeCrab
 */
@Slf4j
@RestController
@RequestMapping("/API")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserVO login(@RequestBody @Validated LoginParam user) {
        return userService.login(user);
    }

    @GetMapping("/test")
    public String test() {
        log.info("---test---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        return "认证通过";
    }
}