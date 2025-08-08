package com.jojo.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.annotations.CacheNamespace;
import com.jojo.mybatis.annotations.Delete;
import com.jojo.mybatis.annotations.Insert;
import com.jojo.mybatis.annotations.Select;
import com.jojo.mybatis.annotations.Update;
import com.jojo.mybatis.cache.PerpetualCache;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.mapping.SqlCommandType;
import com.jojo.mybatis.session.Configuration;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * XML配置构建器
 */
public class XMLConfigBuilder {
    private List<Class<? extends Annotation>> sqlAnnotationTypeList = Lists.newArrayList(Insert.class, Delete.class, Update.class, Select.class);

    public Configuration parse() {
        Configuration configuration = new Configuration();
        // 解析mapper
        parseMapper(configuration);
        return configuration;
    }

    @SneakyThrows
    private void parseMapper(Configuration configuration) {
        // com.jojo.mybatis.demo.mapper
        Set<Class<?>> classes = ClassUtil.scanPackage("com.jojo.demo.mapper");
        for (Class<?> aClass : classes) {
            CacheNamespace cacheNamespace = aClass.getAnnotation(CacheNamespace.class);
            boolean isCache = cacheNamespace != null;
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                SqlCommandType sqlCommandType = null;
                // 原始SQL
                String originalSql = "";
                for (Class<? extends Annotation> sqlAnnotationType : sqlAnnotationTypeList) {
                    Annotation annotation = method.getAnnotation(sqlAnnotationType);
                    if (annotation == null) {
                        continue;
                    }
                    originalSql = (String) annotation.getClass().getMethod("value").invoke(annotation);
                    if (annotation instanceof Insert) {
                        sqlCommandType = SqlCommandType.INSERT;
                    } else if (annotation instanceof Delete) {
                        sqlCommandType = SqlCommandType.DELETE;
                    } else if (annotation instanceof Update) {
                        sqlCommandType = SqlCommandType.UPDATE;
                    } else if (annotation instanceof Select) {
                        sqlCommandType = SqlCommandType.SELECT;
                    } else {
                        throw new RuntimeException("AnnotationType is invalid. annotationType = " + annotation);
                    }

                }

                // 拿到mapper的返回类型
                Class returnType = null;
                boolean isSelectMany = false;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                    isSelectMany = true;
                } else if (genericReturnType instanceof Class) {
                    returnType = (Class) genericReturnType;
                }

                // 封装
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(aClass.getName() + "." + method.getName())
                        .sql(originalSql)
                        .returnType(returnType)
                        .sqlCommandType(sqlCommandType)
                        .isSelectMany(isSelectMany)
                        .cache(isCache ? new PerpetualCache(aClass.getName()) : null)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }


    }
}
