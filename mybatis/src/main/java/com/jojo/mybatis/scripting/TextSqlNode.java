package com.jojo.mybatis.scripting;

import com.jojo.mybatis.parsing.BindingTokenHandler;
import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import lombok.SneakyThrows;

/**
 * 文本SQL节点
 */
public class TextSqlNode implements SqlNode {
    private String text; // eg: select * from t_user where id = ${id}

    public TextSqlNode(String text) {
        this.text = text;
    }

    @SneakyThrows
    @Override
    public void apply(DynamicContext context) {
        // context.appendSql(text);
        // 解析参数，直接替换值
        BindingTokenHandler tokenHandler = new BindingTokenHandler(context);
        GenericTokenParser parser = new GenericTokenParser("${", "}", tokenHandler);
        String sql = parser.parse(text);
        context.appendSql(sql);
    }
}
