package org.jojo.mybatis.demo;

import cn.hutool.json.JSONUtil;
import com.jojo.mybatis.session.SqlSession;
import com.jojo.mybatis.session.SqlSessionFactory;
import com.jojo.mybatis.session.SqlSessionFactoryBuilder;
import org.jojo.mybatis.demo.entity.User;
import org.jojo.mybatis.demo.service.UserService;
import org.jojo.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *  测试
 */
@ComponentScan("org.jojo.mybatis.demo")
@MapperScan("org.jojo.mybatis.demo.mapper")
public class App {
    @Bean
    public SqlSession sqlSession() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.findOne(2);
        System.out.println(JSONUtil.toJsonStr(user));
    }
}
