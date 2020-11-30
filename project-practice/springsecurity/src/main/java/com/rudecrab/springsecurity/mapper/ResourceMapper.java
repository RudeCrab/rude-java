package com.rudecrab.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rudecrab.springsecurity.model.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author RudeCrab
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据角色id删除该角色下权限
     * @param roleId 角色id
     * @return 受影响的行数
     */
    int deleteByRoleId(Serializable roleId);

    /**
     * 根据角色id增加角色权限
     * @param roleId 角色id
     * @param resourceIds 权限id集合
     * @return 受影响的行数
     */
    int insertResourcesByRoleId(@Param("roleId") Long roleId, @Param("resourceIds") Collection<Long> resourceIds);

    /**
     * 根据用户id获取权限id
     * @param userId 用户id
     * @return 权限id集合
     */
    Set<Long> selectIdsByUserId(Long userId);

    /**
     * 根据角色id获取权限id
     * @param roleId 角色id
     * @return 权限id集合
     */
    Set<Long> selectIdsByRoleId(Long roleId);

    /**
     * 批量新增权限资源
     * @param resources 资源对象集合
     * @return 受影响的行数
     */
    int insertResources(@Param("resources") Collection<Resource> resources);

    /**
     * 根据用户id获取该用户的所有权限资源对象
     * @param userId 用户id
     * @return 权限资源集合
     */
    List<Resource> selectListByUserId(Long userId);
}
