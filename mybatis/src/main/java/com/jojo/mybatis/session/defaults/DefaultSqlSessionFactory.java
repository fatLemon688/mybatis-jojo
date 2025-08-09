package com.jojo.mybatis.session.defaults;

import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.session.SqlSession;
import com.jojo.mybatis.session.SqlSessionFactory;
import com.jojo.mybatis.transaction.JDBCTransaction;
import com.jojo.mybatis.transaction.Transaction;

/**
 * 默认的SqlSession工厂
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        Transaction transaction = new JDBCTransaction(configuration.getDataSource(), autoCommit);
        return new DefaultSqlSession(configuration.newExecutor(transaction), configuration);
    }

    @Override
    public SqlSession openSession() {
        return openSession(true);
    }
}
