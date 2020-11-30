package com.rudecrab.springsecurity.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rudecrab.springsecurity.annotation.Auth;
import com.rudecrab.springsecurity.model.entity.Role;
import com.rudecrab.springsecurity.model.param.RoleParam;
import com.rudecrab.springsecurity.model.vo.RolePageVO;
import com.rudecrab.springsecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 角色
 *
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/role")
@Auth(id = 2000, name = "角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @Auth(id = 1, name = "新增角色")
    public String createUser(@RequestBody @Validated(RoleParam.CreateRole.class) RoleParam param) {
        roleService.createRole(param);
        return "操作成功";
    }

    @DeleteMapping
    @Auth(id = 2, name = "删除角色")
    public String deleteUser(Long[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        return "操作成功";
    }

    @PutMapping
    @Auth(id = 3, name = "编辑角色")
    public String updateMenus(@RequestBody @Validated(RoleParam.UpdateResources.class) RoleParam param) {
        roleService.updateResources(param);
        return "操作成功";
    }

    @GetMapping("/list")
    public List<Role> getRoleList() {
        return roleService.list();
    }

    @GetMapping("/page/{current}")
    public IPage<RolePageVO> getPage(@PathVariable("current") int current) {
        // 设置分页参数
        Page<RolePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).addOrder(orderItem);
        return roleService.selectPage(page);
    }
}
