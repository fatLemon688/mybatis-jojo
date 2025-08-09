package com.jojo.mybatis.mapping;

import com.jojo.mybatis.cache.Cache;
import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import com.jojo.mybatis.scripting.DynamicContext;
import com.jojo.mybatis.scripting.SqlNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * mapper配置信息
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {
    // 唯一表示 eg: com.jojo.demo.mapper.UserMapper.selectList
    private String id;

    private String sql;

    // 返回类型
    private Class returnType;

    // SQL命令类型
    private SqlCommandType sqlCommandType;

    // 是否查询多条数据
    private Boolean isSelectMany;

    private Cache cache; // 缓存

    private SqlNode sqlSource; // 动态SQL
    public BoundSql getBoundSql(Object parameter) {
        // sql解析：#{} --> ?
        if (sqlSource != null) {
            DynamicContext dynamicContext = new DynamicContext((Map<String, Object>) parameter);
            sqlSource.apply(dynamicContext);
            sql = dynamicContext.getSql();
        }
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(this.sql);
        List<String> parameterMappings = tokenHandler.getParameterMappings();
        return BoundSql.builder().sql(sql).parameterMappings(parameterMappings).build();
    }

    public String getCacheKey(Object parameter) {
        return id + ":" + sql + ":" + parameter;
    }
}
