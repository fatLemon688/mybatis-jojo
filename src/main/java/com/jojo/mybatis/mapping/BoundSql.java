package com.jojo.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
/**
 * SQL & 参数映射
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoundSql {
    private String sql;

    private List<String> parameterMappings;
}
