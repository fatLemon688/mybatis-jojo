package com.jojo.mybatis.executor.resultSet;

import com.jojo.mybatis.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.util.List;

/**
 *  结果处理器
 */
public interface ResultSetHandler {
    <T> List<T> handleResultSet(MappedStatement ms, PreparedStatement ps);
}
