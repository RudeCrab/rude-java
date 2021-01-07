package com.rudecrab.di.chain;

/**
 * @author RudeCrab
 */
public class MyRequest {
    /**
     * 请求人姓名
     */
    private String name;
    /**
     * 请假天数。为了演示就简单按整天来算，不弄什么小时了
     */
    private Integer day;

    public MyRequest(String name, Integer day) {
        this.name = name;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public Integer getDay() {
        return day;
    }
}
