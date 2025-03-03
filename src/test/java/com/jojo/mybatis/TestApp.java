package com.jojo.mybatis;

import cn.hutool.json.JSONUtil;
import com.jojo.mybatis.binding.MapperProxyFactory;
import demo.entity.User;
import demo.mapper.UserMapper;
import org.junit.Test;

import java.util.List;

// 测试
public class TestApp {
    @Test
    public void test() throws Exception {
        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class);
        List<User> users = userMapper.selectList(1, "jojo");
        System.out.println(JSONUtil.toJsonStr(users));
        /*User user = userMapper.selectOne(2);
        System.out.println(user);*/
    }
}
