package com.jojo.mybatis.executor;

import com.jojo.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * sql执行器
 */
public interface Executor {
    <T> List<T> query(MappedStatement ms, Object parameter);

    int update(MappedStatement ms, Object parameter);
}
