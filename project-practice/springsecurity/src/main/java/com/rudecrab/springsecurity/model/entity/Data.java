package com.rudecrab.springsecurity.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author RudeCrab
 */
@lombok.Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("data")
public class Data extends BaseEntity {
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
