package com.jojo.mybatis;

import com.jojo.mybatis.demo.entity.User;
import com.jojo.mybatis.demo.mapper.UserMapper;
import org.junit.Test;

import java.util.List;

// 测试
public class TestApp {
    @Test
    public void test() throws Exception {
        UserMapper userMapper = UserMapper.class.newInstance();
        List<User> users = userMapper.selectList();
        System.out.println(users);
    }
}
