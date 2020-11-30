package com.rudecrab.springsecurity.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author RudeCrab
 */
@Data
@Accessors(chain = true)
public class UserVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录认证token
     */
    private String token;
    /**
     * 当前用户的权限资源id集合
     */
    private Set<Long> resourceIds;
}
