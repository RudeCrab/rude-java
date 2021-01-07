package com.rudecrab.di.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Order(3)
@Service
public class BossHandler implements Handler {
    @Override
    public boolean process(MyRequest request) {
        System.out.println(String.format("Boss已审批【%s】的【%d】天请假申请", request.getName(), request.getDay()));
        // 中止
        return false;
    }
}
