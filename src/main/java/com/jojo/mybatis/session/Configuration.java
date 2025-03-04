package com.jojo.mybatis.session;

import com.jojo.mybatis.mapping.MappedStatement;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心配置
 */
@Data
public class Configuration {
    // eg: com.jojo.demo.mapper.UserMapper.selectList ----> mapper配置信息
    private Map<String, MappedStatement> mappedStatementMap= new HashMap<>();

    public void addMappedStatement(MappedStatement ms) {
        this.mappedStatementMap.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatementMap.get(id);
    }
}
