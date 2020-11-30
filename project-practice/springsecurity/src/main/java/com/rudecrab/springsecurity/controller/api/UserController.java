package com.rudecrab.springsecurity.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rudecrab.springsecurity.enums.ResultCode;
import com.rudecrab.springsecurity.exception.ApiException;
import com.rudecrab.springsecurity.model.param.UserParam;
import com.rudecrab.springsecurity.model.vo.UserPageVO;
import com.rudecrab.springsecurity.service.ResourceService;
import com.rudecrab.springsecurity.service.UserService;
import com.rudecrab.springsecurity.annotation.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

/**
 * 用户接口
 *
 * @author RudeCrab
 */
@Slf4j
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("/user/test/{}:{}", id, authentication.toString());
        return "测试";
    }
}
