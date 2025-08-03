package com.jojo.mybatis;

import cn.hutool.json.JSONUtil;
import com.jojo.demo.entity.User;
import com.jojo.demo.mapper.UserMapper;
import com.jojo.mybatis.builder.XMLConfigBuilder;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.jojo.mybatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

// 测试
public class TestApp {
    @Test
    public void test() throws Exception {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse();
        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectList(1, "jojo");
        System.out.println(JSONUtil.toJsonStr(users));
        /*User user = userMapper.selectOne(2);
        System.out.println(user);*/
    }
}
