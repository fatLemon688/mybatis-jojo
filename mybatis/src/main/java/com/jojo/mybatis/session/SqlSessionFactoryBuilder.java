package com.jojo.mybatis.session;

import com.jojo.mybatis.builder.XMLConfigBuilder;
import com.jojo.mybatis.session.defaults.DefaultSqlSessionFactory;

/**
 * SqlSession工厂构建者
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build() {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse();
        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);
        return sessionFactory;
    }
}
