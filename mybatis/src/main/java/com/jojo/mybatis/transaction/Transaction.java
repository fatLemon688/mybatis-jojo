package com.jojo.mybatis.transaction;

import java.sql.Connection;

/**
 *  事务管理
 */
public interface Transaction {

    Connection getConnection();

    void commit();

    void rollback();

    void close();
}
