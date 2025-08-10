package org.jojo.mybatis.demo.mapper;

import com.jojo.mybatis.annotations.CacheNamespace;
import com.jojo.mybatis.annotations.Insert;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import org.jojo.mybatis.demo.entity.User;


// 用户mapper
@CacheNamespace
public interface UserMapper {
    @Select("select * from t_user where id = #{id}")
    User findOne(@Param("id")Integer id);

    @Insert("insert into t_user(name, age) values(#{user.name}, #{user.age})")
    Long insert(@Param("user")User user);
}
