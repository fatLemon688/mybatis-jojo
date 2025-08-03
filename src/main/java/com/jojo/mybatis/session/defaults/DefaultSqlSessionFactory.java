package com.jojo.mybatis.session.defaults;

import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.session.SqlSession;
import com.jojo.mybatis.session.SqlSessionFactory;

/**
 * 默认的SqlSession工厂
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration.newExecutor(), configuration);
    }
}
