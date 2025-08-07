package com.jojo.mybatis.executor.parameter;

import cn.hutool.core.util.ReflectUtil;
import com.jojo.mybatis.session.Configuration;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 *  默认参数处理器
 */
public class DefaultParameterHandler implements ParameterHandler {
    private Configuration configuration;

    public DefaultParameterHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @SneakyThrows
    @Override
    public void setParam(PreparedStatement ps, Object parameter, List<String> parameterMappings) {
        //设置值
        Map<Class, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();
        Map<String, Object> paramValueMap = (Map<String, Object>) parameter;
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
    }
}
