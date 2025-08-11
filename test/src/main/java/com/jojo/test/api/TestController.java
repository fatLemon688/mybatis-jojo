package com.jojo.test.api;

import com.jojo.test.mapper.DemoMapper;
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
    private DemoMapper demoMapper;

    @GetMapping("/test")
    public Object test() {
        return demoMapper.findOne(1);
    }
}
