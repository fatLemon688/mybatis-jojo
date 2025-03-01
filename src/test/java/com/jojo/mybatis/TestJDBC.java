package com.jojo.mybatis;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 测试原生jdbc
public class TestJDBC {
    @Test
    public void test() throws Exception {
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
        // 构建sql和执行sql
        PreparedStatement ps = connection.prepareStatement("select * from t_user");
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
    }
}
