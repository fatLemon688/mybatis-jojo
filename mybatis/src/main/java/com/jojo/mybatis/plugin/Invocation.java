package com.jojo.mybatis.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

/**
 *  代理对象所需参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invocation {
    private Object target; // 代理对象

    private Method method; // 执行方法

    private Object[] args; // 方法参数

    @SneakyThrows
    public Object proceed() {
        return method.invoke(target, args);
    }
}
