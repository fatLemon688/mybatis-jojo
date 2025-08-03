package com.jojo.mybatis.session;

import com.jojo.mybatis.binding.MapperProxyFactory;
import com.jojo.mybatis.executor.Executor;
import com.jojo.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * 默认的SqlSession
 */
public class DefaultSqlSession implements SqlSession{
    private Executor executor;

    private Configuration configuration;

    public DefaultSqlSession(Executor executor, Configuration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    @Override
    public int insert(String statementId, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statementId);
        return executor.update(ms, parameter);
    }

    @Override
    public int delete(String statementId, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statementId);
        return executor.update(ms, parameter);
    }

    @Override
    public int update(String statementId, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statementId);
        return executor.update(ms, parameter);
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        List<T> list = selectList(statementId, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> selectList(String statementId, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statementId);
        return executor.query(ms, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> mapper) {
        return MapperProxyFactory.getProxy(mapper, this);
    }
}
