package com.jojo.mybatis.session;

/**
 * 生产SqlSession
 */
public interface SqlSessionFactory {
    SqlSession openSession(boolean autoCommit);

    SqlSession openSession();
}
