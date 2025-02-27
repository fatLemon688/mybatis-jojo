package com.jojo.mybatis.design.proxy._02;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import java.lang.reflect.Method;

// jdk动态代理
public class JdkProxy implements InvocationHandler {
    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    /**
     * 第一个参数：类加载器
     * 第二个参数：增强方法所在的类，这个类实现的接口，表示这个代理类可以执行哪些方法
     * 第三个参数：实现InvocationHandler接口
     *
     */
    public <T> T getProxy(Class<T> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("invoke before");
        Object result = method.invoke(target, objects);
        System.out.println("invoke after");
        return result;
    }

    public static void main(String[] args) {
        JdkProxy jdkProxy = new JdkProxy(new UserServiceImpl());
        UserService userService = jdkProxy.getProxy(UserService.class);
        System.out.println(userService.selectOne("xx"));
        System.out.println(userService.selectList("666"));
    }
}
