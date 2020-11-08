package com.rudecrab.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author RudeCrab
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity{
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
}
