package com.rudecrab.springsecurity.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author RudeCrab
 */
@Data
public class DataPageVO {
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 客户手机
     */
    private String customerPhone;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 公司名称
     */
    private String companyName;
}
