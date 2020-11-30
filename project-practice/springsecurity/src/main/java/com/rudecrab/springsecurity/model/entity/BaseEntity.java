package com.rudecrab.springsecurity.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 基础实体类，所有实体对象集成此类
 *
 * @author RudeCrab
 */
@Data
public abstract class BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
}
