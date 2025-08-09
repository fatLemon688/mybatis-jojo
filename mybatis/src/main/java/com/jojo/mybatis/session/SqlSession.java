package com.jojo.mybatis.session;

import java.util.List;

/**
 * 操作增删改查
 */
public interface SqlSession {
    int insert(String statementId, Object parameter);

    int delete(String statementId, Object parameter);

    int update(String statementId, Object parameter);

    <T> T selectOne(String statementId, Object parameter);

    <T> List<T> selectList(String statementId, Object parameter);

    <T> T getMapper(Class<T> mapper);

    Configuration getConfiguration();

    void commit();

    void rollback();

    void close();
}
