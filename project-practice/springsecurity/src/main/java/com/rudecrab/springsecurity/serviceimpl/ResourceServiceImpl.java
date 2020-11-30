package com.rudecrab.springsecurity.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rudecrab.springsecurity.mapper.ResourceMapper;
import com.rudecrab.springsecurity.model.entity.Resource;
import com.rudecrab.springsecurity.service.ResourceService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author RudeCrab
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public void insertResources(Collection<Resource> resources) {
        if (Collections.isEmpty(resources)) {
            return;
        }
        // 再新增接口类型的资源
        baseMapper.insertResources(resources);
    }

    @Override
    public void deleteResourceByType(int type) {
        // 先删除所有接口类型的资源
        LambdaUpdateWrapper<Resource> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Resource::getType, type);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<Resource> getResourcesByUserId(Long userId) {

        return baseMapper.selectListByUserId(userId);
    }
}