package com.jojo.mybatis.binding;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.type.IntegerTypeHandler;
import com.jojo.mybatis.type.StringTypeHandler;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// mapper代理
public class MapperProxy implements InvocationHandler {

    private Map<Class, TypeHandler> typeHandlerMap = new HashMap<>();

    private Configuration configuration;
    
    private Class mapperClass;

    public MapperProxy(Configuration configuration, Class mapperClass) {
        this.configuration = configuration;
        this.mapperClass = mapperClass;
        this.typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        this.typeHandlerMap.put(String.class, new StringTypeHandler());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();

        MappedStatement ms = configuration.getMappedStatement(mapperClass.getName()+ "." + method.getName());
        // 拿到sql
        String originalSql = ms.getSql();

        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(originalSql);


        // 构建sql和执行sql
        // jdbc里#{}替换规则：#{}---->?
        PreparedStatement ps = connection.prepareStatement(sql);

        //设置值
        // 获取mapper方法对应的（参数名 ---> 参数值）
        Map<String, Object> paramValueMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            String paramName = param.value();
            paramValueMap.put(paramName, args[i]);
        }
        List<String> parameterMappings = tokenHandler.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            // parameterMappings这个List:使字段顺序跟字段名绑定
            // paramValueMap:使字段名跟字段值绑定
            // 这样能使字段顺序跟字段值对应上，再使用PreparedStatement来设置值
            String columName = parameterMappings.get(i);
            Object val = paramValueMap.get(columName);
            typeHandlerMap.get(val.getClass()).setParameter(ps, i + 1, val);
        }
        ps.execute();

        // 拿到mapper的返回类型
        Class returnType = ms.getReturnType();

        // 拿到结果集
        ResultSet rs = ps.getResultSet();
        // 把ResultSet里的每一条数据转成User对象存到list

        // 拿到sql返回字段名称
        List<String> columnList = Lists.newArrayList();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            columnList.add(metaData.getColumnName(i + 1));
        }

        List instanceList = Lists.newArrayList();
        while (rs.next()) {
            // 结果映射
            Object instance = returnType.newInstance();
            for (String columnName : columnList) {
                Field field = ReflectUtil.getField(returnType, columnName);
                Object value = typeHandlerMap.get(field.getType()).getResult(rs, columnName);
                ReflectUtil.setFieldValue(instance, columnName, value);
            }
            instanceList.add(instance);
        }
        // 释放资源
        rs.close();
        ps.close();
        connection.close();
        return instanceList;
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
}
