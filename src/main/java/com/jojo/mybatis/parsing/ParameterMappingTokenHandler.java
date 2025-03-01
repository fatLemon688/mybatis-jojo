package com.jojo.mybatis.parsing;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *  参数处理器
 */

public class ParameterMappingTokenHandler implements TokenHandler{
    private List<String> parameterMappings = Lists.newArrayList();

    public List<String> getParameterMappings() {
        return this.parameterMappings;
    }
    @Override
    public String handleToken(String content) {
        parameterMappings.add(content);
        return "?";
    }
}
