package com.jojo.mybatis.executor.dataSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 代理连接
 */
public class PooledConnection implements InvocationHandler {
    private Connection connection;

    private Connection proxyConnection;

    private PooledSource dataSource;

    public PooledConnection(PooledSource dataSource, Connection connection) {
        this.connection = connection;
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("close".equals(method.getName())) {
            dataSource.returnConnection(proxyConnection);
        } else {
            method.invoke(connection, args);
        }
        return null;
    }

    public Connection getProxy() {
        proxyConnection = (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(), this);
        return proxyConnection;
    }
}
