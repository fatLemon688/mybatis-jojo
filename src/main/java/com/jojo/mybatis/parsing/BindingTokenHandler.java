package com.jojo.mybatis.parsing;

import com.jojo.mybatis.scripting.DynamicContext;
import lombok.SneakyThrows;
import ognl.Ognl;

/**
 *  ${}参数处理器
 */

public class BindingTokenHandler implements TokenHandler{
    private DynamicContext context;

    public BindingTokenHandler(DynamicContext context) {
        this.context = context;
    }

    @SneakyThrows
    @Override
    public String handleToken(String content) {
        return String.valueOf(Ognl.getValue(content, context.getBindings()));
    }
}
