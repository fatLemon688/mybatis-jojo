package com.jojo.mybatis.plugin;

import com.google.common.collect.Sets;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  插件代理
 */
public class Plugin implements InvocationHandler {
    private Object target;

    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return interceptor.intercept(new Invocation(target, method, args));
    }

    public static  <T> T warp(Object target, Interceptor interceptor) {
        if (target.getClass().isAssignableFrom(null)) {
            return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new Plugin(target, interceptor));
        } else {
            return (T) target;
        }
    }

    @SneakyThrows
    private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
        // class --> query, update
        Intercepts intercepts = interceptor.getClass().getAnnotation(Intercepts.class);
        Signature[] signatures = intercepts.value();
        HashMap<Class<?>, Set<Method>> result = new HashMap<>();
        for (Signature signature : signatures) {
            Class<?> type = signature.type();
            String methodName = signature.method();
            Class<?>[] args = signature.args();
            Method method = type.getMethod(methodName, args);
            Set<Method> methods = result.get(type);
            if (methods == null) {
                result.put(type, Sets.newHashSet(method));
            } else {
                methods.add(method);
            }
        }
        return result;
    }
}
