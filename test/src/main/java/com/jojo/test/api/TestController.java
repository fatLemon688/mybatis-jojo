package com.jojo.test.api;

import org.jojo.mybatis.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public Object test() {
        return userMapper.findOne(1);
    }
}
