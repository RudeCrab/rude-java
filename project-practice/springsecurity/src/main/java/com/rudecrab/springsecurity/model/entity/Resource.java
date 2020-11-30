package com.rudecrab.springsecurity.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 资源
 *
 * @author RudeCrab
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("resource")
public class Resource extends BaseEntity{
    /**
     * 路径
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型。0为菜单，1为接口
     */
    private Integer type;
}
