package com.jojo.mybatis.session;

import com.jojo.mybatis.executor.Executor;
import com.jojo.mybatis.executor.SimpleExecutor;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.plugin.InterceptorChain;
import com.jojo.mybatis.plugin.LimitInterceptor;
import com.jojo.mybatis.plugin.SqlInterceptor;
import com.jojo.mybatis.type.IntegerTypeHandler;
import com.jojo.mybatis.type.StringTypeHandler;
import com.jojo.mybatis.type.TypeHandler;
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

    private Map<Class, TypeHandler> typeHandlerMap = new HashMap<>();

    private InterceptorChain interceptorChain = new InterceptorChain();

    public Configuration() {
        this.typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        this.typeHandlerMap.put(String.class, new StringTypeHandler());
        interceptorChain.addInterceptor(new LimitInterceptor());
        //interceptorChain.addInterceptor(new SqlInterceptor());
    }

    public void addMappedStatement(MappedStatement ms) {
        this.mappedStatementMap.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatementMap.get(id);
    }

    public Executor newExecutor() {
        return (Executor) interceptorChain.pluginAll(new SimpleExecutor(this));
    }
}
