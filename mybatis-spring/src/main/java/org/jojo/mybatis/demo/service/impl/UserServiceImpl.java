package org.jojo.mybatis.demo.service.impl;

import org.jojo.mybatis.demo.entity.User;
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

    @Override
    public User findOne(Integer id) {
        User user = userMapper.findOne(id);
        System.out.println(user);
        return user;
    }
}
