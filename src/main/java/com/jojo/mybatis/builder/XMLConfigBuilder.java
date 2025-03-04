package com.jojo.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.jojo.mybatis.annotations.Select;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.session.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * XML配置构建器
 */
public class XMLConfigBuilder {
    public Configuration parse() {
        Configuration configuration = new Configuration();
        // 解析mapper
        parseMapper(configuration);
        return configuration;
    }

    private void parseMapper(Configuration configuration) {
        // com.jojo.mybatis.demo.mapper
        Set<Class<?>> classes = ClassUtil.scanPackage("com.jojo.demo.mapper");
        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                // 拿到sql
                Select select = method.getAnnotation(Select.class);
                String originalSql = select.value();

                // 拿到mapper的返回类型
                Class returnType = null;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                } else if (genericReturnType instanceof Class) {
                    returnType = (Class) genericReturnType;
                }

                // 封装
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(aClass.getName()+ "." + method.getName())
                        .sql(originalSql)
                        .returnType(returnType)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }


    }
}
