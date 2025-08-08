package com.jojo.mybatis;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// 测试原生jdbc
public class TestJDBCTransaction {
    @Test
    public void test() throws Exception {
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
        System.out.println("是否自动提交：" + connection.getAutoCommit());
        // 设置手动提交
        connection.setAutoCommit(false);
        // 构建sql和执行sql
        PreparedStatement ps = connection.prepareStatement("INSERT INTO t_user (name, age) VALUES ('haku', 20)");
        ps.execute();

        // 拿到更新操作数
        int updateCount = ps.getUpdateCount();
        System.out.println(updateCount);
        try {
            handleBusiness();
            connection.commit(); // 提交事务
        } catch (Exception e) {
            connection.rollback(); // 回滚事务
            throw new RuntimeException(e);
        }

        // 释放资源
        ps.close();
        connection.close();
    }

    private void handleBusiness() {
        int i = 1 / 0; //假设这里是业务逻辑
    }
}
