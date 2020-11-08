package com.rudecrab.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rudecrab.rbac.annotation.Auth;
import com.rudecrab.rbac.enums.ResultCode;
import com.rudecrab.rbac.exception.ApiException;
import com.rudecrab.rbac.model.param.UserParam;
import com.rudecrab.rbac.model.vo.UserPageVO;
import com.rudecrab.rbac.service.ResourceService;
import com.rudecrab.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

/**
 * 用户接口
 *
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/user")
@Auth(id = 1000, name = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @PostMapping
    @Auth(id = 1, name = "新增用户")
    public String createUser(@RequestBody @Validated(UserParam.CreateUser.class) UserParam param) {
        userService.createUser(param);
        return "操作成功";
    }

    @DeleteMapping
    @Auth(id = 2, name = "删除用户")
    public String deleteUser(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        userService.removeByIds(Arrays.asList(ids));
        return "操作成功";
    }

    @PutMapping
    @Auth(id = 3, name = "编辑用户")
    public String updateRoles(@RequestBody @Validated(UserParam.Update.class) UserParam param) {
        userService.update(param);
        return "操作成功";
    }

    @GetMapping("/page/{current}")
    public IPage<UserPageVO> getPage(@PathVariable("current") int current) {
        // 设置分页参数
        Page<UserPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).addOrder(orderItem);
        return userService.selectPage(page);
    }

    @GetMapping("/resources/{userId}")
    public Set<Long> getResourcesByUserId(@PathVariable("userId") Long userId) {
        return resourceService.getIdsByUserId(userId);
    }

    @Auth(id = 4,name = "用于演示路径参数")
    @GetMapping("/test/{id}")
    public String testInterface(@PathVariable("id") String id) {
        return "测试";
    }
}
