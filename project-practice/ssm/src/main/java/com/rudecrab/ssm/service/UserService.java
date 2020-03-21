package com.rudecrab.ssm.service;

import com.rudecrab.ssm.entity.User;

import java.util.List;

/**
 * User类业务层接口，用来声明关于User的业务层方法
 * @author RC
 */
public interface UserService {
    /**
     * 从数据库中查询出所有的User对象
     * @return User对象集合
     */
    List<User> getAll();
}
