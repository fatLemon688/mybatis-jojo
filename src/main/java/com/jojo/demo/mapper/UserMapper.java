package com.jojo.demo.mapper;

import com.jojo.mybatis.annotations.Delete;
import com.jojo.mybatis.annotations.Insert;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import com.jojo.demo.entity.User;
import com.jojo.mybatis.annotations.Update;

import java.util.List;

// 用户mapper
public interface UserMapper {

    // @param注解作用：（参数映射）应对由于参数顺序不同导致方法重载的问题，这样的话同样的sql，不管参数顺序怎么变，只需要一个方法就行
    @Select("select * from t_user")
    List<User> selectList(@Param("id")Integer id, @Param("name")String name);

    @Select("select * from t_user where id = #{id}")
    User selectOne(@Param("id")Integer id);

    @Insert("insert into t_user(name, age) values(#{user.name}, #{user.age})")
    Long insert(@Param("user") User user);

    @Delete("delete from t_user where id = #{id}")
    Long delete(@Param("id")Integer id);

    @Update("update t_user set name = #{name} where id = #{id}")
    Integer update(@Param("id")Integer id, @Param("name")String name);
}
