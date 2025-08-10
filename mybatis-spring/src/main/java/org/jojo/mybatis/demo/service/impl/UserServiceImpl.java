package org.jojo.mybatis.demo.service.impl;

import org.jojo.mybatis.demo.entity.User;
import org.jojo.mybatis.demo.mapper.TestMapper;
import org.jojo.mybatis.demo.mapper.UserMapper;
import org.jojo.mybatis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  用户实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findOne(Integer id) {
        User user = testMapper.findOne(id);
        User user2 = userMapper.findOne(id);
        System.out.println(user);
        System.out.println(user2);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) {
        System.out.println("userService save...");
        userMapper.insert(user);
//        jdbcTemplate.execute("insert into t_user(name, age) values('testJdbc', 18)");
//        int i = 1 / 0;
    }
}
