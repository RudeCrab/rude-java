package com.rudecrab.rudejava.money.mapper;

import com.rudecrab.rudejava.money.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author RudeCrab
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 根据主键获取用户对象
     * @param id 主键
     * @return 用户对象
     */
    User selectById(Serializable id);

    /**
     * 根据主键更新属性
     * @param user 用户对象
     * @return 受影响的行数
     */
    int updateById(User user);
}
