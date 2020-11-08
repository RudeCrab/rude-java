package com.rudecrab.rbac.controller.api;

import com.rudecrab.rbac.model.entity.User;
import com.rudecrab.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/index")
public class IndexController {
    @Autowired
    private UserService userService;

    @GetMapping("addUser")
    public void addUser() {
        for (int i = 1; i <= 100; i++) {
            User user = new User().setUsername("user" + i).setPassword("user" + i);
            userService.save(user);
        }
    }
}
