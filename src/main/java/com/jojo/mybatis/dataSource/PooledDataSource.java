package com.jojo.mybatis.dataSource;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * 数据库连接池
 */
public class PooledDataSource implements DataSource {
    private final int POOL_SIZE = 10;
    private LinkedBlockingQueue<Connection> pool = new LinkedBlockingQueue<>(POOL_SIZE);

    @SneakyThrows
    public PooledDataSource() {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false", "root", "Hu468502553");
            Connection proxy = new PooledConnection(this, connection).getProxy();
            pool.add(proxy);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return pool.poll();
    }

    public void returnConnection(Connection connection) {
        pool.add(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
