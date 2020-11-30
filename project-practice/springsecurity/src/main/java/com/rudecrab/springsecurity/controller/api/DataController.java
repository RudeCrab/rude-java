package com.rudecrab.springsecurity.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rudecrab.springsecurity.model.vo.DataPageVO;
import com.rudecrab.springsecurity.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping("/page/{current}")
    public IPage<DataPageVO> getPage(@PathVariable("current") int current) {
        // 设置分页参数
        Page<DataPageVO> page = new Page<>();
        // 设置按创建时间倒序
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("create_time");
        orderItem.setAsc(false);
        page.setCurrent(current).addOrder(orderItem);
        return dataService.selectPage(page);
    }
}
