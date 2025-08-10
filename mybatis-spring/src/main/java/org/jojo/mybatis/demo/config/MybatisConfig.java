package org.jojo.mybatis.demo.config;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.jojo.mybatis.session.SqlSession;
import com.jojo.mybatis.session.SqlSessionFactory;
import com.jojo.mybatis.session.SqlSessionFactoryBuilder;
import org.jojo.mybatis.demo.entity.User;
import org.jojo.mybatis.demo.service.UserService;
import org.jojo.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *  测试
 */
@EnableTransactionManagement
@ComponentScan("org.jojo.mybatis.demo")
@MapperScan("org.jojo.mybatis.demo.mapper")
public class MybatisConfig {
    @Bean
    public SqlSession sqlSession() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("Hu468502553");
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MybatisConfig.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println(JSONUtil.toJsonStr(userService.findOne(2)));
        userService.save(new User(6,  DateTime.now() + "jojo", 20));
    }
}
