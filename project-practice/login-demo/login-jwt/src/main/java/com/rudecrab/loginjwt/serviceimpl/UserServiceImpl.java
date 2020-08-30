package com.rudecrab.loginjwt.serviceimpl;

import com.rudecrab.loginjwt.context.UserContext;
import com.rudecrab.loginjwt.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void doSomething() {
        String currentUserName = UserContext.getCurrentUserName();
        System.out.println("Service层---当前登录用户名称：" + currentUserName);
    }
}
