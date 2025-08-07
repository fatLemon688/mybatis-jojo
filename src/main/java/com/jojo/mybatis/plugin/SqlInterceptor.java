package com.jojo.mybatis.plugin;

import com.jojo.mybatis.executor.resultSet.ResultSetHandler;
import com.jojo.mybatis.mapping.MappedStatement;
import com.mysql.cj.jdbc.ClientPreparedStatement;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;

/**
 *  SQL日志打印插件
 */
@Intercepts({
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSet",
                args = {MappedStatement.class, PreparedStatement.class})
})
@Slf4j
public class SqlInterceptor implements Interceptor{
    @SneakyThrows
    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("SQL插件start");
        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[1];
        String sql = "";
        if (ps instanceof ClientPreparedStatement) {
            sql = ((ClientPreparedStatement) ps).asSql();
        }
        System.err.println("执行SQL: " + sql);
        Object result = invocation.proceed();
        System.out.println("SQL插件end");
        return result;
    }

    @Override
    public <T> T plugin(Object target) {
        return Plugin.warp(target, this);
    }
}
