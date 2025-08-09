package com.jojo.mybatis.scripting;

import java.util.Map;
import java.util.StringJoiner;

/**
 * 静态上下文
 */
public class DynamicContext {
    private Map<String, Object> bindings;

    private StringJoiner sqlBuilder =  new StringJoiner(" ");

    public DynamicContext(Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

    public void appendSql(String sqlText) {
        sqlBuilder.add(sqlText.trim());
    }

    public String getSql() {
        return sqlBuilder.toString();
    }
}
