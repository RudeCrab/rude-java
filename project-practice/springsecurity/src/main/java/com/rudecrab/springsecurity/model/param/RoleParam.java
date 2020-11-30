package com.rudecrab.springsecurity.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 角色相关参数
 *
 * @author RudeCrab
 */
@Data
public class RoleParam {
    @NotNull(message = "角色id不能为空", groups = UpdateResources.class)
    private Long id;

    @NotBlank(message = "管理员名称不能为空", groups = CreateRole.class)
    @Length(min = 1, max = 12, message = "用户名长度不能超过12位", groups = CreateRole.class)
    private String name;

    private Set<Long> resourceIds;

    public interface CreateRole {
    }

    public interface UpdateResources {
    }
}
