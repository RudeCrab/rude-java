package com.rudecrab.springsecurity.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author RudeCrab
 */
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private Set<Long> roleIds;
    private Set<Long> companyIds;
}
