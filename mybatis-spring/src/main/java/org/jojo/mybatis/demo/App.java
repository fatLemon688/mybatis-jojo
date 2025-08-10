package org.jojo.mybatis.demo;

import cn.hutool.json.JSONUtil;
import org.jojo.mybatis.demo.entity.User;
import org.jojo.mybatis.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *  测试
 */
@ComponentScan("org.jojo.mybatis.demo")
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.findOne(1);
        System.out.println(JSONUtil.toJsonStr(user));
    }
}
