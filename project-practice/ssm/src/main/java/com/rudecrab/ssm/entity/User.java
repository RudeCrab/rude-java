package com.rudecrab.ssm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 * @author RC
 */
@Data
public class User implements Serializable {
    private Long id;

    private String name;

    private String password;
}
