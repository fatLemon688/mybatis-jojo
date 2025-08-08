package com.jojo.mybatis.transaction;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 *  JDBC事务管理
 */
public class JDBCTransaction implements Transaction {

    private DataSource dataSource;

    private Connection connection;

    private boolean autoCommit;

    public JDBCTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        this.connection = connection;
        return connection;
    }

    @SneakyThrows
    @Override
    public void commit() {
        if (autoCommit) {
            return;
        }
        if (connection != null) {
            connection.commit();
        }
    }

    @SneakyThrows
    @Override
    public void rollback() {
        if (autoCommit) {
            return;
        }
        if (connection != null) {
            connection.rollback();
        }
    }

    @SneakyThrows
    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}
