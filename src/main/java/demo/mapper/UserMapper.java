package demo.mapper;

import com.jojo.mybatis.annotations.Select;
import demo.entity.User;

import java.util.List;

// 用户mapper
public interface UserMapper {

    @Select("select * from t_user where id = #{id} and name = #{name}")
    List<User> selectList(Integer id, String name);
}
