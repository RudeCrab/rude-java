package com.rudecrab.di.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Order(2)
@Service
public class ManagerHandler implements Handler{
    @Override
    public boolean process(MyRequest request) {
        if (request.getDay() <= 7) {
            System.out.println(String.format("Manger已审批【%s】的【%d】天请假申请", request.getName(), request.getDay()));
            // 处理完毕，不放行
            return false;
        }
        System.out.println(String.format("Manger无法审批【%s】的【%d】天请假申请", request.getName(), request.getDay()));
        // 放行
        return true;
    }
}
