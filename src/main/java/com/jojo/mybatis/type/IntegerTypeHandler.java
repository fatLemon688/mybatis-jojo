package com.jojo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Integer字段类型处理器
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter) throws SQLException {
        ps.setInt(i, parameter);
    }
}
