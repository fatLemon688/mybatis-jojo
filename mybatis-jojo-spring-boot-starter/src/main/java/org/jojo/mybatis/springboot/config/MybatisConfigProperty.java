package org.jojo.mybatis.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis属性配置
 */
@Data
@Configuration
@ConfigurationProperties("mybatis-jojo")
public class MybatisConfigProperty {
    // mapper包扫描路径
    private String mapper;
}
