package com.jojo.mybatis.plugin;

/**
 *  拦截器
 */
public interface Interceptor {
    Object intercept(Invocation invocation);
}
