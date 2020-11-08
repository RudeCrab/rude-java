package com.rudecrab.rbac.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rudecrab.rbac.exception.ApiException;
import com.rudecrab.rbac.mapper.UserMapper;
import com.rudecrab.rbac.model.entity.User;
import com.rudecrab.rbac.model.param.UserParam;
import com.rudecrab.rbac.model.param.LoginParam;
import com.rudecrab.rbac.model.vo.UserPageVO;
import com.rudecrab.rbac.model.vo.UserVO;
import com.rudecrab.rbac.security.JwtManager;
import com.rudecrab.rbac.security.UserContext;
import com.rudecrab.rbac.service.CompanyService;
import com.rudecrab.rbac.service.ResourceService;
import com.rudecrab.rbac.service.RoleService;
import com.rudecrab.rbac.service.UserService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author RudeCrab
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;

    @Override
    public UserVO login(LoginParam loginParam) {
        User user = this.lambdaQuery()
                .eq(User::getUsername, loginParam.getUsername())
                .eq(User::getPassword, loginParam.getPassword())
                .one();
        if (user == null) {
            throw new ApiException("账号或密码错误");
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId()).setUsername(loginParam.getUsername())
                .setToken(jwtManager.generate(userVO.getId()))
                .setResourceIds(resourceService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public IPage<UserPageVO> selectPage(Page<UserPageVO> page) {
        QueryWrapper<UserPageVO> queryWrapper = new QueryWrapper<>();
        // 不显示用户1（超级管理账号）也不显示当前登录用户
        queryWrapper.ne("id", 1).ne("id", UserContext.getCurrentUserId());
        // 获取分页列表
        IPage<UserPageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // 再查询各用户的角色和公司id
        for (UserPageVO vo : pages.getRecords()) {
            vo.setRoleIds(roleService.getIdsByUserId(vo.getId()));
            vo.setCompanyIds(companyService.getIdsByUserId(vo.getId()));
        }
        return pages;
    }

    @Override
    public void update(UserParam param) {
        updateRoles(param);
        updateCompany(param);
    }

    private void updateRoles(UserParam param) {
        // 先删除原有数据
        roleService.removeByUserId(param.getId());
        // 如果角色为空就代表删除所有角色，不用后面新增流程了
        if (Collections.isEmpty(param.getRoleIds())) {
            return;
        }
        // 再新增数据
        roleService.insertRolesByUserId(param.getId(), param.getRoleIds());
    }

    private void updateCompany(UserParam param){
        companyService.removeByUserId(param.getId());
        // 如果公司为空就代表删除所有公司，不用后面新增流程了
        if (Collections.isEmpty(param.getCompanyIds())) {
            return;
        }
        // 再新增数据
        companyService.insertCompanyByUserId(param.getId(), param.getCompanyIds());
    }

    @Override
    public void createUser(UserParam param) {
        if (lambdaQuery().eq(User::getUsername, param.getUsername()).one() != null) {
            throw new ApiException("用户名重复");
        }
        User user = new User();
        // 密码默认和账号名一致
        user.setUsername(param.getUsername()).setPassword(param.getUsername());
        save(user);
        if (Collections.isEmpty(param.getRoleIds())) {
            return;
        }
        // 再新增权限数据
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (Collections.isEmpty(idList)) {
            return false;
        }
        // 删除用户下所属的角色
        for (Serializable userId : idList) {
            roleService.removeByUserId(userId);
        }
        // 删除用户
        baseMapper.deleteBatchIds(idList);
        return true;
    }
}
