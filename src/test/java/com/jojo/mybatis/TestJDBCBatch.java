package com.jojo.mybatis;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// 测试原生jdbc
public class TestJDBCBatch {
    @Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
        // 构建sql和执行sql
        PreparedStatement ps = connection.prepareStatement("INSERT INTO `t_user` (`name`, `age`) VALUES (?, ?)");
        for (int i = 0; i < 1000; i++) {
            ps.setString(1,"jojo");
            ps.setInt(2, 18);
            ps.addBatch();

/*            // 每100条执行一次批量插入
            if (i % 100 == 0) {
                ps.executeBatch();
                ps.clearBatch();
            }*/
        }
        ps.executeBatch();

        // 释放资源
        ps.close();
        connection.close();
        System.out.println("耗时: " + (System.currentTimeMillis() - startTime)); // 1300ms
    }
}
