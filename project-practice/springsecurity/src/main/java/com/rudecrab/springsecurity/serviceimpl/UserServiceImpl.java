package com.rudecrab.springsecurity.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rudecrab.springsecurity.exception.ApiException;
import com.rudecrab.springsecurity.mapper.UserMapper;
import com.rudecrab.springsecurity.model.entity.UserEntity;
import com.rudecrab.springsecurity.model.param.LoginParam;
import com.rudecrab.springsecurity.model.param.UserParam;
import com.rudecrab.springsecurity.model.vo.UserPageVO;
import com.rudecrab.springsecurity.model.vo.UserVO;
import com.rudecrab.springsecurity.security.JwtManager;
import com.rudecrab.springsecurity.security.UserDetail;
import com.rudecrab.springsecurity.service.CompanyService;
import com.rudecrab.springsecurity.service.ResourceService;
import com.rudecrab.springsecurity.service.RoleService;
import com.rudecrab.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author RudeCrab
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService, UserDetailsService {
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserVO login(LoginParam param) {
        // 根据用户名查询出用户实体对象
        UserEntity user = baseMapper.selectByUsername(param.getUsername());
        // 若没有查到用户或者密码校验失败则抛出异常
        if (user == null || !passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new ApiException("账号密码错误");
        }

        UserVO userVO = new UserVO();
        userVO.setId(user.getId())
                .setUsername(user.getUsername())
                // 生成token
                .setToken(jwtManager.generate(user.getUsername()))
                .setResourceIds(resourceService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public IPage<UserPageVO> selectPage(Page<UserPageVO> page) {
        QueryWrapper<UserPageVO> queryWrapper = new QueryWrapper<>();
        // 不显示用户1（超级管理账号）TODO 也不显示当前登录用户
        queryWrapper.ne("id", 1);
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
        if (CollectionUtils.isEmpty(param.getRoleIds())) {
            return;
        }
        // 再新增数据
        roleService.insertRolesByUserId(param.getId(), param.getRoleIds());
    }

    private void updateCompany(UserParam param) {
        companyService.removeByUserId(param.getId());
        // 如果公司为空就代表删除所有公司，不用后面新增流程了
        if (CollectionUtils.isEmpty(param.getCompanyIds())) {
            return;
        }
        // 再新增数据
        companyService.insertCompanyByUserId(param.getId(), param.getCompanyIds());
    }

    @Override
    public void createUser(UserParam param) {
        if (lambdaQuery().eq(UserEntity::getUsername, param.getUsername()).one() != null) {
            throw new ApiException("用户名重复");
        }
        UserEntity user = new UserEntity();
        // 密码默认为12345
        user.setUsername(param.getUsername()).setPassword(passwordEncoder.encode("12345"));
        save(user);
        if (CollectionUtils.isEmpty(param.getRoleIds())) {
            return;
        }
        // 再新增权限数据
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 先调用DAO层查询用户实体对象
        UserEntity user = baseMapper.selectByUsername(username);
        // 若没查询到一定要抛出该异常，这样才能被Spring Security的错误处理器处理
        if (user == null) {
            throw new UsernameNotFoundException("没有找到该用户");
        }
        // 查询权限id
        Set<SimpleGrantedAuthority> authorities = resourceService.getIdsByUserId(user.getId())
                .stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        // 走到这代表查询到了实体对象，返回我们自定义的UserDetail对象（这里权限暂时放个空集合，后面我会讲解）
        return new UserDetail(user, authorities);
    }
}
