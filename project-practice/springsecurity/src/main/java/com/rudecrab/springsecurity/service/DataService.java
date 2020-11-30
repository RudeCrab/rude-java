package com.rudecrab.springsecurity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rudecrab.springsecurity.model.entity.Data;
import com.rudecrab.springsecurity.model.vo.DataPageVO;

/**
 * @author RudeCrab
 */
public interface DataService extends IService<Data> {
    /**
     * 获取分页信息
     * @param page 分页参数
     * @return 分页对象
     */
    IPage<DataPageVO> selectPage(Page<DataPageVO> page);
}
