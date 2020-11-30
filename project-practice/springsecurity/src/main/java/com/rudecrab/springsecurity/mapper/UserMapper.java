package com.rudecrab.springsecurity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rudecrab.springsecurity.model.entity.UserEntity;
import com.rudecrab.springsecurity.model.vo.UserPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author RudeCrab
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 查询用户分页信息
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页对象
     */
    IPage<UserPageVO> selectPage(Page<UserPageVO> page, @Param(Constants.WRAPPER) Wrapper<UserPageVO> wrapper);

    /**
     * 根据用户名查询出用户实体对象
     * @param username 用户名
     * @return 用户实体对象
     */
    UserEntity selectByUsername(String username);
}
