package com.jojo.mybatis.design.proxy._03;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// cglib动态代理
public class CglibProxy implements MethodInterceptor {

    public Object getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        // 被代理的目标类
        enhancer.setSuperclass(clazz);
        // 拦截器
        enhancer.setCallback(this);
        // 创建代理类
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib动态代理开始---");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib动态代理结束---");
        return result;
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        UserService proxy = (UserService) cglibProxy.getProxy(UserService.class);
        System.out.println(proxy.selectList("jojo"));
    }
}
