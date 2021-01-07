package com.rudecrab.di.strategy;

/**
 * @author RudeCrab
 */
public interface PostageStrategy {
    /**
     * 计算邮费
     * @param weight 货物重量
     * @return 计算后的邮费
     */
    long calPostage(int weight);
}
