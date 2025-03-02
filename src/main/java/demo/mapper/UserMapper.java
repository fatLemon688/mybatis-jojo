package demo.mapper;

import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import demo.entity.User;

import java.util.List;

// 用户mapper
public interface UserMapper {

    // @param注解作用：（参数映射）应对由于参数顺序不同导致方法重载的问题，这样的话同样的sql，不管参数顺序怎么变，只需要一个方法就行
    @Select("select * from t_user where id = #{id} and name = #{name}")
    List<User> selectList(@Param("id")Integer id, @Param("name")String name);
}
