package com.jojo.mybatis.plugin;

/**
 *  SQL日志打印插件
 */
public class SqlInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("SQL插件start");
        Object result = invocation.proceed();
        System.out.println("SQL插件end");
        return result + "222";
    }
}
