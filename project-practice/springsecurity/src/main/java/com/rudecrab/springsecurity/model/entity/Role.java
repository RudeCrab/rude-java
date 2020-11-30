package com.rudecrab.springsecurity.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色
 *
 * @author RudeCrab
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("role")
public class Role extends BaseEntity {
    /**
     * 名称
     */
    private String name;
}
