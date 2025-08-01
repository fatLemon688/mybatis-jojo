package com.jojo.mybatis.executor;

import com.jojo.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * 批处理执行器
 */
public class BatchExecutor implements Executor{
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        return null;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        return 0;
    }
}
