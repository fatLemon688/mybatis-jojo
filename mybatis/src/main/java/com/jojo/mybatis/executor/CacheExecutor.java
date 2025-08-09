package com.jojo.mybatis.executor;

import com.jojo.mybatis.cache.Cache;
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
        Cache cache = ms.getCache();
        if (cache == null) {
            // 没有开启二级缓存
            return delegate.query(ms, parameter);
        }
        String cacheKey = ms.getCacheKey(parameter);
        Object list = cache.getObject(cacheKey);
        if (list == null) {
            list = delegate.query(ms, parameter);
            cache.putObject(cacheKey, list);
            return (List<T>) list;
        }
        return (List<T>) list;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        ms.getCache().clear();
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
