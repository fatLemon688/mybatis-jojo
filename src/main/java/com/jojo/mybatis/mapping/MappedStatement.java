package com.jojo.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
}
