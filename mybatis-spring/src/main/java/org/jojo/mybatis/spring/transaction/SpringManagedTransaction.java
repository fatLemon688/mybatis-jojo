package org.jojo.mybatis.spring.transaction;

import com.jojo.mybatis.transaction.Transaction;
import lombok.SneakyThrows;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 *  Spring事务管理
 */
public class SpringManagedTransaction implements Transaction {

    private DataSource dataSource;

    private Connection connection;

    private boolean autoCommit;

    public SpringManagedTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        this.connection = connection;
        this.autoCommit = connection.getAutoCommit();
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
