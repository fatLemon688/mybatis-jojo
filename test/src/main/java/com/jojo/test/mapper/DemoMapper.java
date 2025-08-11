package com.jojo.test.mapper;

import com.jojo.demo.entity.User;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;

/**
 *  demo mapper
 */
public interface DemoMapper {
    @Select("select * from t_user where id = #{id}")
    User findOne(@Param("id")Integer id);
}
