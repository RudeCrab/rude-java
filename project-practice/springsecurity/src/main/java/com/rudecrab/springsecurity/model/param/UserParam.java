package com.rudecrab.springsecurity.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户相关参数
 *
 * @author RudeCrab
 */
@Data
public class UserParam {
    @NotNull(message = "用户id不能为空", groups = Update.class)
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = CreateUser.class)
    @Length(min = 4, max = 12, message = "用户名长度为4-12位", groups = CreateUser.class)
    private String username;

    private List<Long> roleIds;

    private List<Long> companyIds;

    public interface Update {}

    public interface CreateUser{}
}
