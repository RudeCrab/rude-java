package com.rudecrab.di.chain;

/**
 * @author RudeCrab
 */
public interface Handler {
    /**
     * 处理请假申请
     * @param request 请假申请
     * @return true则放行，false则中止
     */
    boolean process(MyRequest request);
}
