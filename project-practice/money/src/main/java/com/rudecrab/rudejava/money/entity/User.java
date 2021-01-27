package com.rudecrab.rudejava.money.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author RudeCrab
 */
@Data
public class User {
    private Long id;
    private BigDecimal balance;
    private Long otherBalance;

    public void addBalance(User param) {
        if (balance == null) {return;}
        balance = balance.add(param.getBalance());
        otherBalance += param.getOtherBalance();
    }

    public void subBalance(User param) {
        if (balance == null) {return;}
        balance = balance.subtract(param.getBalance());
        otherBalance -= param.getOtherBalance();
    }

    public void multiBalance(User param) {
        if (balance == null) {return;}
        balance = balance.multiply(param.getBalance());
        otherBalance *= param.getOtherBalance();
    }

    public void divBalance(User param) {
        if (balance == null || otherBalance == null) {return;}
        if (param.getBalance().doubleValue() == 0 || param.otherBalance == 0) {return;}
        balance = balance.divide(param.getBalance(), 2, RoundingMode.DOWN);
        otherBalance /= param.getOtherBalance();
    }
}
