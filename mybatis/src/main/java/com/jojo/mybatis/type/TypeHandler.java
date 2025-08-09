package com.jojo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段类型处理器
 */
public interface TypeHandler<T> {
    /**
     *
     * @param ps    PreparedStatement
     * @param i     参数索引
     * @param parameter     参数值
     * @throws SQLException
     */
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    /**
     *
     * @param rs    ResultSet
     * @param column    字段名
     * @return  字段值
     * @throws SQLException
     */
    T getResult(ResultSet rs, String column) throws SQLException;
}
