package com.rudecrab.rudejava.money.service;

import com.rudecrab.rudejava.money.entity.User;
import com.rudecrab.rudejava.money.enums.CalType;

import java.io.Serializable;

/**
 * @author RudeCrab
 */
public interface UserService {
    User getById(Serializable id);

    void update(User user);

    User calBalance(CalType calType, User user);
}
