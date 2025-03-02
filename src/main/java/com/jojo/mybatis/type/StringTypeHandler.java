package com.jojo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * String字段类型处理器
 */
public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter) throws SQLException {
        ps.setString(i, parameter);
    }
}
