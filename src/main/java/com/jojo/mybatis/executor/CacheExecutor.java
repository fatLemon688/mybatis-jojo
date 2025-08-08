package com.jojo.mybatis.executor;

import com.jojo.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 *  缓存执行器
 */
public class CacheExecutor implements Executor {
    private Executor delegate;

    public CacheExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        return delegate.query(ms, parameter);
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        return delegate.update(ms, parameter);
    }

    @Override
    public void commit() {
        delegate.commit();
    }

    @Override
    public void rollback() {
        delegate.rollback();
    }

    @Override
    public void close() {
        delegate.close();
    }
}
