package com.jojo.mybatis.scripting;

import lombok.SneakyThrows;
import ognl.Ognl;

import java.util.Map;

/**
 * if SQL节点
 */
public class IfSqlNode implements SqlNode {
    private String test; // eg: id != null

    private SqlNode sqlNode; // eg: IfSqlNode

    public IfSqlNode(String test, SqlNode sqlNode) {
        this.test = test;
        this.sqlNode = sqlNode;
    }

    @SneakyThrows
    @Override
    public void apply(Map context) {
        Boolean value = (Boolean)Ognl.getValue(test, context);
        if (value) {
            sqlNode.apply(context);
        }
    }
}
