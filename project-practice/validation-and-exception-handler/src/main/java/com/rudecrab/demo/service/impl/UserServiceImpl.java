package com.rudecrab.demo.service.impl;

import com.rudecrab.demo.entity.User;
import com.rudecrab.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author RC
 * @description 用户业务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String addUser(User user) {
        // 直接编写业务逻辑
        return "success";
    }
}
