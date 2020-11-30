package com.rudecrab.springsecurity.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author RudeCrab
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
}
