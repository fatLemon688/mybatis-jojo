package com.jojo.mybatis;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.jojo.demo.entity.User;
import com.jojo.demo.mapper.UserMapper;
import com.jojo.mybatis.session.SqlSessionFactory;
import com.jojo.mybatis.session.SqlSessionFactoryBuilder;
import com.jojo.mybatis.session.SqlSession;
import org.junit.Test;

import java.util.List;


// 测试
public class TestApp {
    @Test
    public void test() throws Exception {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sessionFactoryBuilder.build();
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        System.out.println(JSONUtil.toJsonStr(userMapper.findOne(2)));
        sqlSession.commit();
        sqlSession.close();

//        SqlSession sqlSession2 = sqlSessionFactory.openSession(false);
//        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
//        System.out.println(JSONUtil.toJsonStr(userMapper2.selectList(1, "jojo")));
//        System.out.println(JSONUtil.toJsonStr(userMapper2.selectList(1, "jojo")));
//        System.out.println(JSONUtil.toJsonStr(userMapper.selectList(2, "jojo")));
//        User user = userMapper.selectOne(1);
//        System.out.println(user);
//        System.out.println(userMapper.insert(User.builder().age(RandomUtil.randomInt()).name(RandomUtil.randomString(5)).build()));
//        System.out.println(JSONUtil.toJsonStr(userMapper.selectList(1, "jojo")));
//        System.out.println(JSONUtil.toJsonStr(userMapper.selectList(1, "jojo")));
//        System.out.println(userMapper.delete(3));
//        System.out.println(userMapper.update(5, "testUpdate"));
//        sqlSession2.commit();
//        sqlSession2.close();
    }
}
