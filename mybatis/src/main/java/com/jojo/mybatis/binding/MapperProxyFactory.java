package com.jojo.mybatis.binding;

import com.jojo.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

// mapper代理工厂
public class MapperProxyFactory {
    /**
     * 第一个参数：类加载器
     * 第二个参数：增强方法所在的类，这个类实现的接口，表示这个代理类可以执行哪些方法
     * 第三个参数：实现InvocationHandler接口
     *
     */
    public static <T> T getProxy(Class<T> mapperClass, SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new MapperProxy(sqlSession, mapperClass));
    }
}
