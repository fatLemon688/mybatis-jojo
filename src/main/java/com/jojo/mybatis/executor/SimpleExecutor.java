package com.jojo.mybatis.executor;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.mapping.BoundSql;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * 简单执行器
 */
public class SimpleExecutor implements Executor{
    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = new Configuration();
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        Connection connection = getConnection();
        PreparedStatement ps = execute(connection, ms, parameter);
        List<T> resultSet = configuration.newResultSetHandler().handleResultSet(ms, ps);
        // 释放资源
        connection.close();
        return resultSet;
    }

    @SneakyThrows
    @Override
    public int update(MappedStatement ms, Object parameter) {
        Connection connection = getConnection();
        PreparedStatement ps = execute(connection, ms, parameter);
        // 拿到操作数
        int updateCount = ps.getUpdateCount();
        // 释放资源
        ps.close();
        connection.close();
        return updateCount;
    }

    @SneakyThrows
    private static Connection getConnection() {
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        return DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
    }

    @SneakyThrows
    private PreparedStatement execute(Connection connection, MappedStatement ms, Object parameter) {
        // 构建sql和执行sql
        BoundSql boundSql = ms.getBoundSql();
        PreparedStatement ps = connection.prepareStatement(boundSql.getSql());
        // 设置值
        configuration.newParameterHandler().setParam(ps, parameter, boundSql.getParameterMappings());
        ps.execute();
        return ps;
    }
}
