package com.jojo.mybatis.plugin;

import com.jojo.mybatis.executor.statement.PreparedStatementHandler;
import com.jojo.mybatis.executor.statement.StatementHandler;
import com.jojo.mybatis.mapping.BoundSql;

import java.sql.Connection;

/**
 *  分页插件
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class})
})
public class LimitInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) {
        PreparedStatementHandler psh = (PreparedStatementHandler) invocation.getTarget();
        BoundSql boundSql = psh.getBoundSql();
//        System.out.println("分页插件start");
        String sql = boundSql.getSql();
        if (!sql.contains("LIMIT")) {
            boundSql.setSql(sql +  " LIMIT 2");
        }
        Object result = invocation.proceed();
//        System.out.println("分页插件end");
        return result;
    }

    @Override
    public <T> T plugin(Object target) {
        return Plugin.warp(target, this);
    }
}
