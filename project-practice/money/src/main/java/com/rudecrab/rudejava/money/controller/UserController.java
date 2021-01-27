package com.rudecrab.rudejava.money.controller;

import com.rudecrab.rudejava.money.entity.User;
import com.rudecrab.rudejava.money.enums.CalType;
import com.rudecrab.rudejava.money.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id")Long id) {
        return userService.getById(id);
    }

    @PostMapping("/add")
    public User add(@RequestBody User user) {
        return userService.calBalance(CalType.ADD, user);
    }

    @PostMapping("/subtract")
    public User subtract(@RequestBody User user) {
        return userService.calBalance(CalType.SUBTRACT, user);
    }

    @PostMapping("/multiply")
    public User multiply(@RequestBody User user) {
        return userService.calBalance(CalType.MULTIPLY, user);
    }

    @PostMapping("/divide")
    public User divide(@RequestBody User user) {
        return userService.calBalance(CalType.DIVIDE, user);
    }
}
