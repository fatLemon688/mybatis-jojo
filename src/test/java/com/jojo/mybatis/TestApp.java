package com.jojo.mybatis;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.jojo.demo.entity.User;
import com.jojo.demo.mapper.UserMapper;
import com.jojo.mybatis.session.SqlSessionFactoryBuilder;
import com.jojo.mybatis.session.SqlSession;
import org.junit.Test;

import java.util.List;


// 测试
public class TestApp {
    @Test
    public void test() throws Exception {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSession sqlSession = sessionFactoryBuilder.build().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = userMapper.selectList(1, "jojo");
        System.out.println(JSONUtil.toJsonStr(users));
        User user = userMapper.selectOne(1);
        System.out.println(user);
        System.out.println(userMapper.insert(User.builder().age(RandomUtil.randomInt()).name(RandomUtil.randomString(5)).build()));
        System.out.println(userMapper.delete(3));
        System.out.println(userMapper.update(5, "testUpdate"));
    }
}
