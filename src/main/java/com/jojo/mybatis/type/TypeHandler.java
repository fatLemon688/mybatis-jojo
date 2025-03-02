package com.jojo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 字段类型处理器
 */
public interface TypeHandler<T> {
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;
}
