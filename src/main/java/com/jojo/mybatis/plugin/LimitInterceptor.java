package com.jojo.mybatis.plugin;

/**
 *  分页插件
 */
public class LimitInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) {
        System.out.println("分页插件start");
        Object result = invocation.proceed();
        System.out.println("分页插件end");
        return result;
    }
}
