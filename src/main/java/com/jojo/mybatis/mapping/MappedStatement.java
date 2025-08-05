package com.jojo.mybatis.mapping;

import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    public BoundSql getBoundSql() {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(this.sql);
        List<String> parameterMappings = tokenHandler.getParameterMappings();
        return BoundSql.builder().sql(sql).parameterMappings(parameterMappings).build();
    }
}
