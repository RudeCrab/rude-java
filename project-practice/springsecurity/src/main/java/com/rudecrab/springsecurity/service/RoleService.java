package com.rudecrab.springsecurity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rudecrab.springsecurity.model.entity.Role;
import com.rudecrab.springsecurity.model.param.RoleParam;
import com.rudecrab.springsecurity.model.vo.RolePageVO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author RudeCrab
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户id获取角色id
     * @param userId 用户id
     * @return 该用户的角色id集合
     */
    Set<Long> getIdsByUserId(Long userId);

    /**
     * 根据用户id删除该用户的所有角色
     * @param userId 用户id
     */
    void removeByUserId(Serializable userId);

    /**
     * 根据用户id批量增加角色
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void insertRolesByUserId(Long userId, Collection<Long> roleIds);

    /**
     * 获取分页信息
     * @param page 分页参数
     * @return 分页对象
     */
    IPage<RolePageVO> selectPage(Page<RolePageVO> page);

    /**
     * 更新角色权限
     * @param param 入参
     */
    void updateResources(RoleParam param);

    /**
     * 创建角色
     * @param param 入参
     */
    void createRole(RoleParam param);
}
