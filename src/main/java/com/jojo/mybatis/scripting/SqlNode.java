package com.jojo.mybatis.scripting;

import java.util.Map;

/**
 * SQL节点
 */
public interface SqlNode {
    void apply(Map context);
}
