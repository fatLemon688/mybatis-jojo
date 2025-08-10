package com.jojo.mybatis.plugin;

import com.jojo.mybatis.executor.statement.StatementHandler;
import com.mysql.cj.jdbc.ClientPreparedStatement;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *  SQL日志打印插件
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class}),
        @Signature(
                type = StatementHandler.class,
                method = "update",
                args = {Statement.class}),
})
@Slf4j
public class SqlLogInterceptor implements Interceptor{
    @SneakyThrows
    @Override
    public Object intercept(Invocation invocation) {
//        System.out.println("SQL插件start");
        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
        String sql = "";
        if (ps instanceof ClientPreparedStatement) {
           sql = ((ClientPreparedStatement) ps).getPreparedSql();
        }
        log.info("执行SQL: " + sql);
        Object result = invocation.proceed();
//        System.out.println("SQL插件end");
        return result;
    }

    @Override
    public <T> T plugin(Object target) {
        return Plugin.warp(target, this);
    }
}
