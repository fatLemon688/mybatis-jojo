package com.jojo.mybatis.executor;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * 简单执行器
 */
public class SimpleExecutor implements Executor{
    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = new Configuration();
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object parameter) {
        Connection connection = getConnection();

        // 拿到sql
        String originalSql = ms.getSql();

        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(originalSql);


        // 构建sql和执行sql
        // jdbc里#{}替换规则：#{}---->?
        PreparedStatement ps = connection.prepareStatement(sql);

        Map<Class, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();

        //设置值
        Map<String, Object> paramValueMap = (Map<String, Object>) parameter;
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
    @Override
    public int update(MappedStatement ms, Object parameter) {
        Connection connection = getConnection();

        // 拿到sql
        String originalSql = ms.getSql();

        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(originalSql);


        // 构建sql和执行sql
        // jdbc里#{}替换规则：#{}---->?
        PreparedStatement ps = connection.prepareStatement(sql);

        Map<Class, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();

        //设置值
        Map<String, Object> paramValueMap = (Map<String, Object>) parameter;
        List<String> parameterMappings = tokenHandler.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            // parameterMappings这个List:使字段顺序跟字段名绑定
            // paramValueMap:使字段名跟字段值绑定
            // 这样能使字段顺序跟字段值对应上，再使用PreparedStatement来设置值
            String columName = parameterMappings.get(i);
            if (columName.contains(".")) {
                String[] split = columName.split("\\.");
                String key = split[0];
                Object instanceVal = paramValueMap.get(key);
                String fieldName = split[1];
                Object fieldValue = ReflectUtil.getFieldValue(instanceVal, fieldName);
                typeHandlerMap.get(fieldValue.getClass()).setParameter(ps, i + 1, fieldValue);
            } else {
                Object val = paramValueMap.get(columName);
                typeHandlerMap.get(val.getClass()).setParameter(ps, i + 1, val);
            }

        }
        ps.execute();

        // 拿到操作数
        int updateCount = ps.getUpdateCount();
        // 释放资源
        ps.close();
        connection.close();
        return updateCount;
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
