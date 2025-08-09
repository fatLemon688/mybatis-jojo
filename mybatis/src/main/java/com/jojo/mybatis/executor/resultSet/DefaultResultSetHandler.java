package com.jojo.mybatis.executor.resultSet;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 *  默认结果处理器
 */
public class DefaultResultSetHandler implements ResultSetHandler {
    private Configuration configuration;

    public DefaultResultSetHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @SneakyThrows
    @Override
    public <T> List<T> handleResultSet(MappedStatement ms, PreparedStatement ps) {
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

        Map<Class, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();
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
        rs.close();
        ps.close();
        return instanceList;
    }
}
