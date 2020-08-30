package com.rudecrab.loginsession.serviceimpl;

import com.rudecrab.loginsession.context.RequestContext;
import com.rudecrab.loginsession.entity.User;
import com.rudecrab.loginsession.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void doSomething() {
        User user = RequestContext.getCurrentUser();
        System.out.println("service层---当前登录用户对象：" + user);
    }
}
