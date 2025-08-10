package org.jojo.mybatis.demo.service.impl;

import org.jojo.mybatis.demo.entity.User;
import org.jojo.mybatis.demo.mapper.TestMapper;
import org.jojo.mybatis.demo.mapper.UserMapper;
import org.jojo.mybatis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  用户实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestMapper testMapper;

    @Override
    public User findOne(Integer id) {
        User user = testMapper.findOne(id);
        User user2 = userMapper.findOne(id);
        System.out.println(user);
        System.out.println(user2);
        return user;
    }
}
