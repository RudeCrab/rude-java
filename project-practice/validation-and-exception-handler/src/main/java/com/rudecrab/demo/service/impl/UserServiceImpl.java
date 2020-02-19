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
        if (user == null || user.getId() == null || user.getAccount() == null || user.getPassword() == null || user.getEmail() == null) {
            return "对象或者对象字段不能为空";
        }
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())) {
            return "不能输入空字符串";
        }
        if (user.getAccount().length() < 6 || user.getAccount().length() > 11) {
            return "账号长度必须是6-11个字符";
        }
        if (user.getPassword().length() < 6 || user.getPassword().length() > 16) {
            return "账号长度必须是6-16个字符";
        }
        if (!Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", user.getEmail())) {
            return "邮箱格式不正确";
        }
        return "success";
    }
}
