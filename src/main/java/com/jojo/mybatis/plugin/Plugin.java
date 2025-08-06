package com.jojo.mybatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 *  插件代理
 */
public class Plugin implements InvocationHandler {
    private Object target;

    private List<Interceptor> interceptorList;

    public Plugin(Object target, List<Interceptor> interceptorList) {
        this.target = target;
        this.interceptorList = interceptorList;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method...");
        for (Interceptor interceptor : interceptorList) {
            // 遇事不决，抽成对象
            interceptor.intercept(new Invocation(target, method, args));
        }
        System.out.println("after method...");
        return "1";
    }

    public static  <T> T warp(T target, List<Interceptor> interceptorList) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new Plugin(target, interceptorList));
    }
}
