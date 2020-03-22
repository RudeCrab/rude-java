package com.rudecrab.ssm.service.impl;

import com.rudecrab.ssm.entity.User;
import com.rudecrab.ssm.mapper.UserMapper;
import com.rudecrab.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User类业务层实现类，用来实现关于User的业务层方法
 * @author RC
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public int addOne(User user) {
        return userMapper.insert(user);
    }
}
