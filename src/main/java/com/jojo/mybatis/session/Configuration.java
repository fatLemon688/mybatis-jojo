package com.jojo.mybatis.session;

import com.jojo.mybatis.executor.Executor;
import com.jojo.mybatis.executor.SimpleExecutor;
import com.jojo.mybatis.dataSource.PooledDataSource;
import com.jojo.mybatis.executor.parameter.DefaultParameterHandler;
import com.jojo.mybatis.executor.parameter.ParameterHandler;
import com.jojo.mybatis.executor.resultSet.DefaultResultSetHandler;
import com.jojo.mybatis.executor.resultSet.ResultSetHandler;
import com.jojo.mybatis.executor.statement.PreparedStatementHandler;
import com.jojo.mybatis.executor.statement.StatementHandler;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.plugin.InterceptorChain;
import com.jojo.mybatis.plugin.LimitInterceptor;
import com.jojo.mybatis.plugin.SqlLogInterceptor;
import com.jojo.mybatis.transaction.Transaction;
import com.jojo.mybatis.type.IntegerTypeHandler;
import com.jojo.mybatis.type.StringTypeHandler;
import com.jojo.mybatis.type.TypeHandler;
import lombok.Data;

import javax.sql.DataSource;
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

    private DataSource dataSource = new PooledDataSource();

    public Configuration() {
        this.typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        this.typeHandlerMap.put(String.class, new StringTypeHandler());
        interceptorChain.addInterceptor(new LimitInterceptor());
        interceptorChain.addInterceptor(new SqlLogInterceptor());
    }

    public void addMappedStatement(MappedStatement ms) {
        this.mappedStatementMap.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatementMap.get(id);
    }

    public Executor newExecutor(Transaction transaction) {
        return (Executor) interceptorChain.pluginAll(new SimpleExecutor(this, transaction));
    }

    public ResultSetHandler newResultSetHandler() {
        return (ResultSetHandler) interceptorChain.pluginAll(new DefaultResultSetHandler(this));
    }
    public ParameterHandler newParameterHandler() {
        return (ParameterHandler) interceptorChain.pluginAll(new DefaultParameterHandler(this));
    }

    public StatementHandler newStatementHandler(MappedStatement ms, Object parameter) {
        return (StatementHandler) interceptorChain.pluginAll(new PreparedStatementHandler(this, ms, parameter));
    }
}
