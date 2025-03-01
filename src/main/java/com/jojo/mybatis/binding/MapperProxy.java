package com.jojo.mybatis.binding;

import com.jojo.mybatis.annotations.Select;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.*;

// mapper代理
public class MapperProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();

        // 拿到sql
        Select select = method.getAnnotation(Select.class);
        String sql = select.value();

        // 构建sql和执行sql
        // jdbc里#{}替换规则：#{}---->?
        PreparedStatement ps = connection.prepareStatement("select * from t_user where id = ? and name = ?");
        ps.setInt(1, 1);
        ps.setString(2, "jojo");
        ps.execute();

        // 拿到结果集
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString("name") + " -- " + rs.getInt("age"));
        }
        // 释放资源
        rs.close();
        ps.close();
        connection.close();
        return null;
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
}
