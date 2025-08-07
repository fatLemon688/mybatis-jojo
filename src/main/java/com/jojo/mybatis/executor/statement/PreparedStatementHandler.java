package com.jojo.mybatis.executor.statement;

import com.jojo.mybatis.executor.parameter.ParameterHandler;
import com.jojo.mybatis.executor.resultSet.ResultSetHandler;
import com.jojo.mybatis.mapping.BoundSql;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.session.Configuration;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *  预编译语句处理器
 */
public class PreparedStatementHandler implements StatementHandler {
    private Configuration configuration;

    private MappedStatement ms;

    private Object parameter;

    private ParameterHandler parameterHandler;

    private ResultSetHandler resultSetHandler;

    private BoundSql boundSql;

    public PreparedStatementHandler(Configuration configuration, MappedStatement ms, Object parameter) {
        this.configuration = configuration;
        this.ms = ms;
        this.parameter = parameter;
        this.parameterHandler = configuration.newParameterHandler();
        this.resultSetHandler = configuration.newResultSetHandler();
        boundSql = ms.getBoundSql();
    }

    @SneakyThrows
    @Override
    public Statement prepare(Connection connection) {
        return connection.prepareStatement(boundSql.getSql());
    }

    @Override
    public void parameterize(Statement statement) {
        PreparedStatement ps = (PreparedStatement) statement;
        parameterHandler.setParam(ps, parameter, boundSql.getParameterMappings());
    }

    @SneakyThrows
    @Override
    public <T> T query(Statement statement) {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return (T) resultSetHandler.handleResultSet(ms, ps);
    }

    @SneakyThrows
    @Override
    public int update(Statement statement) {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return ps.getUpdateCount();
    }
}
