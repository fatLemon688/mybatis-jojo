package com.jojo.mybatis.scripting;

import lombok.SneakyThrows;

/**
 * 静态文本SQL节点
 */
public class StaticTextSqlNode implements SqlNode {
    private String text; // eg: select * from t_user

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @SneakyThrows
    @Override
    public void apply(DynamicContext context) {
        context.appendSql(text);
    }
}
