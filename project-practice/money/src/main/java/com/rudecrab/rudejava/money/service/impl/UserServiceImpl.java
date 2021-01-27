package com.rudecrab.rudejava.money.service.impl;

import com.rudecrab.rudejava.money.entity.User;
import com.rudecrab.rudejava.money.enums.CalType;
import com.rudecrab.rudejava.money.mapper.UserMapper;
import com.rudecrab.rudejava.money.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author RudeCrab
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Serializable id) {
        return userMapper.selectById(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User calBalance(CalType calType, User param) {
        User user = getById(param.getId());
        if (user == null) {
            return null;
        }
        switch (calType) {
            case ADD:
                user.addBalance(param);
                break;
            case SUBTRACT:
                user.subBalance(param);
                break;
            case DIVIDE:
                user.divBalance(param);
                break;
            case MULTIPLY:
                user.multiBalance(param);
                break;
            default: return null;
        }
        update(user);
        return user;
    }
}
