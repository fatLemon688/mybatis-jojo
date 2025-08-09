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
        // parameterMappings这个List:使字段顺序跟字段名绑定
        parameterMappings.add(content);
        return "?";
    }
}
