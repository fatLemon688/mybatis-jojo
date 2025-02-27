package com.jojo.mybatis.demo.mapper;

import com.jojo.mybatis.annotations.Select;
import com.jojo.mybatis.demo.entity.User;

import java.util.List;

// 用户mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<User> selectList();
}
