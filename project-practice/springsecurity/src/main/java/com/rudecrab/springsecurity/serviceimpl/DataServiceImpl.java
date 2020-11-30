package com.rudecrab.springsecurity.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rudecrab.springsecurity.mapper.DataMapper;
import com.rudecrab.springsecurity.model.entity.Data;
import com.rudecrab.springsecurity.model.vo.DataPageVO;
import com.rudecrab.springsecurity.service.DataService;
import org.springframework.stereotype.Service;

/**
 * @author RudeCrab
 */
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data> implements DataService {
    @Override
    public IPage<DataPageVO> selectPage(Page<DataPageVO> page) {
        QueryWrapper<DataPageVO> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectPage(page, queryWrapper);
    }
}
