package com.jojo.mybatis.executor;

import com.jojo.mybatis.cache.Cache;
import com.jojo.mybatis.cache.PerpetualCache;
import com.jojo.mybatis.executor.statement.StatementHandler;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.transaction.Transaction;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * 简单执行器
 */
public class SimpleExecutor implements Executor{
    private Configuration configuration;

    private Transaction transaction;

    private Cache localCache;

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        localCache = new PerpetualCache("LocalCache");
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        String cacheKey = ms.getCacheKey(parameter);
        Object list = localCache.getObject(cacheKey);
        if (list != null) {
            return (List<T>) list;
        }
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = prepareStatement(statementHandler);
        list = statementHandler.query(statement);
        localCache.putObject(cacheKey, list);
        return (List<T>) list;
    }

    private Statement prepareStatement(StatementHandler statementHandler) {
        Connection connection = getConnection();
        Statement statement = statementHandler.prepare(connection);
        statementHandler.parameterize(statement);
        return statement;
    }

    @SneakyThrows
    @Override
    public int update(MappedStatement ms, Object parameter) {
        localCache.clear();
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = prepareStatement(statementHandler);
        return statementHandler.update(statement);
    }

    @Override
    public void commit() {
        transaction.commit();
    }

    @Override
    public void rollback() {
        transaction.rollback();
    }

    @Override
    public void close() {
        transaction.close();
    }

    @SneakyThrows
    private Connection getConnection() {
        return transaction.getConnection();
    }
}
