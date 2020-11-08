package com.rudecrab.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rudecrab.rbac.model.entity.User;
import com.rudecrab.rbac.model.param.UserParam;
import com.rudecrab.rbac.model.param.LoginParam;
import com.rudecrab.rbac.model.vo.UserPageVO;
import com.rudecrab.rbac.model.vo.UserVO;

/**
 * @author RudeCrab
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param user 用户参数
     * @return 登录成功则返回vo对象，失败则抛出异常
     */
    UserVO login(LoginParam user);

    /**
     * 获取分页信息
     * @param page 分页参数
     * @return 分页对象
     */
    IPage<UserPageVO> selectPage(Page<UserPageVO> page);

    /**
     * 修改用户信息
     * @param param 入参
     */
    void update(UserParam param);

    /**
     * 新增账户
     * @param param 入参
     */
    void createUser(UserParam param);
}
