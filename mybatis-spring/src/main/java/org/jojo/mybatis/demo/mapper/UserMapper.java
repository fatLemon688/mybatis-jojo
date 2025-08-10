package org.jojo.mybatis.demo.mapper;

import com.jojo.mybatis.annotations.CacheNamespace;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import org.jojo.mybatis.demo.entity.User;


// 用户mapper
@CacheNamespace
public interface UserMapper {
    @Select("select * from t_user where id = #{id}")
    User findOne(@Param("id")Integer id);
}
