package com.rudecrab.springsecurity.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rudecrab.springsecurity.exception.ApiException;
import com.rudecrab.springsecurity.mapper.ResourceMapper;
import com.rudecrab.springsecurity.mapper.RoleMapper;
import com.rudecrab.springsecurity.model.entity.Role;
import com.rudecrab.springsecurity.model.param.RoleParam;
import com.rudecrab.springsecurity.model.vo.RolePageVO;
import com.rudecrab.springsecurity.service.RoleService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author RudeCrab
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public void removeByUserId(Serializable userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    public void insertRolesByUserId(Long userId, Collection<Long> roleIds) {
        baseMapper.insertRolesByUserId(userId, roleIds);
    }

    @Override
    public IPage<RolePageVO> selectPage(Page<RolePageVO> page) {
        QueryWrapper<RolePageVO> queryWrapper = new QueryWrapper<>();
        // 获取分页列表
        IPage<RolePageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // 再查询各角色的权限
        for (RolePageVO vo : pages.getRecords()) {
            vo.setResourceIds(resourceMapper.selectIdsByRoleId(vo.getId()));
        }
        return pages;
    }

    @Override
    public void updateResources(RoleParam param) {
        // 先删除原有数据
        resourceMapper.deleteByRoleId(param.getId());
        // 如果角色为空就代表删除所有角色，不用后面新增流程了
        if (Collections.isEmpty(param.getResourceIds())) {
            return;
        }
        // 再新增数据
        resourceMapper.insertResourcesByRoleId(param.getId(), param.getResourceIds());
    }

    @Override
    public void createRole(RoleParam param) {
        if (lambdaQuery().eq(Role::getName, param.getName()).one() != null) {
            throw new ApiException("角色名重复");
        }
        // 新增角色
        Role role = new Role().setName(param.getName());
        save(role);
        if (Collections.isEmpty(param.getResourceIds())) {
            return;
        }
        // 再新增权限数据
        resourceMapper.insertResourcesByRoleId(role.getId(), param.getResourceIds());
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (Collections.isEmpty(idList)) {
            return false;
        }
        // 删除角色下所属的权限
        for (Serializable roleId : idList) {
            resourceMapper.deleteByRoleId(roleId);
        }
        // 删除角色
        baseMapper.deleteBatchIds(idList);
        return true;
    }
}
