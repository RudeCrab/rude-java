package com.rudecrab.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rudecrab.springsecurity.model.entity.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author RudeCrab
 */
public interface ResourceService extends IService<Resource> {
    /**
     * 根据用户id获取该用户的所有权限id
     * @param userId 用户id
     * @return 权限id集合
     */
    Set<Long> getIdsByUserId(Long userId);

    /**
     * 批量新增接口类型的资源
     * @param resources 资源对象集合
     */
    void insertResources(Collection<Resource> resources);

    /**
     * 根据类型删除资源
     * @param type 资源类型，0为页面权限，1为操作权限
     */
    void deleteResourceByType(int type);

    /**
     * 根据用户id获取该用户的所有权限资源对象
     * @param userId 用户id
     * @return 权限资源集合
     */
    List<Resource> getResourcesByUserId(Long userId);
}
