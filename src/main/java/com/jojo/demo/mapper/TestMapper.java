package com.jojo.demo.mapper;

import com.jojo.mybatis.annotations.CacheNamespace;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import com.jojo.demo.entity.User;

// 测试mapper
@CacheNamespace
public interface TestMapper {
    @Select("select * from t_user where id = #{id}")
    User selectOne(@Param("id")Integer id);
}
