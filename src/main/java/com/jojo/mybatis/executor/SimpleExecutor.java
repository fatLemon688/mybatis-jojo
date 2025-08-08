package com.jojo.mybatis.executor;

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

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        StatementHandler statementHandler = configuration.newStatementHandler(ms, parameter);
        Statement statement = prepareStatement(statementHandler);
        return statementHandler.query(statement);
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
