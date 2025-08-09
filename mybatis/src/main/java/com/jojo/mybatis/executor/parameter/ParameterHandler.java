package com.jojo.mybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.util.List;

/**
 *  参数处理器
 */
public interface ParameterHandler {
    void setParam(PreparedStatement ps, Object parameter, List<String> parameterMappings);
}
