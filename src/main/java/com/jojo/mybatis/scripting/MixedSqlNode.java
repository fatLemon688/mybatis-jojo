package com.jojo.mybatis.scripting;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

/**
 * 混合SQL节点
 */
public class MixedSqlNode implements SqlNode {
    private List<SqlNode> contents; // IfSqlNode, xx

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @SneakyThrows
    @Override
    public void apply(Map context) {
        for (SqlNode content : contents) {
            content.apply(context);
        }
    }
}
