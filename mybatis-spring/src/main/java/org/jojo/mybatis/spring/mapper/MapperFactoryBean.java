package org.jojo.mybatis.spring.mapper;

import com.jojo.mybatis.session.SqlSession;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  mapper工厂bean
 */
public class MapperFactoryBean<T> implements FactoryBean<T> {
    private Class<T> mapperInterface;

    @Autowired
    private SqlSession sqlSession;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return (T) sqlSession.getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
