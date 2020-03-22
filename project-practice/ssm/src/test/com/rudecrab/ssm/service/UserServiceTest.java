package com.rudecrab.ssm.service;

import com.rudecrab.ssm.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void getAll() {
        System.out.println(userService.getAll());
        System.out.println(userService.getAll());
    }

    @Test
    public void addOne() {
        User user = new User();
        user.setName("user");
        user.setPassword("123456");
        System.out.println(userService.addOne(user));
    }
}