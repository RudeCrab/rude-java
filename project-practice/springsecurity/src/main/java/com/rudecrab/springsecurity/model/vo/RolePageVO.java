package com.rudecrab.springsecurity.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * 角色分页对象
 *
 * @author RudeCrab
 */
@Data
public class RolePageVO {
    private Long id;
    private String name;
    private Set<Long> resourceIds;
}
