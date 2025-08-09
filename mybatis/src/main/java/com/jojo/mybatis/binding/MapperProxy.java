package com.jojo.mybatis.binding;

import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.mapping.SqlCommandType;
import com.jojo.mybatis.session.SqlSession;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// mapper代理
public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;
    
    private Class mapperClass;

    public MapperProxy(SqlSession sqlSession, Class mapperClass) {
        this.sqlSession = sqlSession;
        this.mapperClass = mapperClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 获取mapper方法对应的（参数名 ---> 参数值）
        Map<String, Object> paramValueMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            String paramName = param.value();
            paramValueMap.put(paramName, args[i]);
        }

        String statementId = mapperClass.getName()+ "." + method.getName();
        MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(statementId);
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        switch (sqlCommandType) {
            case INSERT:
                return convertResult(ms, sqlSession.insert(statementId, paramValueMap));
            case DELETE:
                return convertResult(ms, sqlSession.delete(statementId, paramValueMap));
            case UPDATE:
                return convertResult(ms, sqlSession.update(statementId, paramValueMap));
            case SELECT:
                if (ms.getIsSelectMany()) {
                    return sqlSession.selectList(statementId, paramValueMap);
                } else {
                    return sqlSession.selectOne(statementId, paramValueMap);
                }
            default:
                break;
        }
        return null;
    }

    @SneakyThrows
    private static Connection getConnection() {
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        return DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
    }

    private Object convertResult(MappedStatement ms, int updateCount) {
        Class returnType = ms.getReturnType();
        if (returnType == int.class || returnType == Integer.class) {
            return updateCount;
        } else if (returnType == Long.class) {
            return Long.valueOf(updateCount);
        } else if (returnType == void.class) {
            return null;
        }
        return null;
    }
}
