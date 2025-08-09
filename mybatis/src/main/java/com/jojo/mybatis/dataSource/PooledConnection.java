package com.jojo.mybatis.dataSource;

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

    private PooledDataSource dataSource;

    public PooledConnection(PooledDataSource dataSource, Connection connection) {
        this.connection = connection;
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if ("close".equals(method.getName())) {
            dataSource.returnConnection(proxyConnection);
        } else {
            result = method.invoke(connection, args);
        }
        return result;
    }

    public Connection getProxy() {
        Connection proxy = (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(), this);
        proxyConnection = proxy;
        return proxy;
    }
}
