package com.jojo.mybatis.builder;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.annotations.CacheNamespace;
import com.jojo.mybatis.annotations.Delete;
import com.jojo.mybatis.annotations.Insert;
import com.jojo.mybatis.annotations.Select;
import com.jojo.mybatis.annotations.Update;
import com.jojo.mybatis.cache.PerpetualCache;
import com.jojo.mybatis.mapping.MappedStatement;
import com.jojo.mybatis.mapping.SqlCommandType;
import com.jojo.mybatis.scripting.IfSqlNode;
import com.jojo.mybatis.scripting.MixedSqlNode;
import com.jojo.mybatis.scripting.SqlNode;
import com.jojo.mybatis.scripting.StaticTextSqlNode;
import com.jojo.mybatis.scripting.TextSqlNode;
import com.jojo.mybatis.session.Configuration;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * XML配置构建器
 */
public class XMLConfigBuilder {
    private List<Class<? extends Annotation>> sqlAnnotationTypeList = Lists.newArrayList(Insert.class, Delete.class, Update.class, Select.class);

    public Configuration parse() {
        Configuration configuration = new Configuration();
        // 解析mapper
        parseMapper(configuration);
        parseMapperXml(configuration);
        return configuration;
    }

    @SneakyThrows
    private void parseMapper(Configuration configuration) {
        // com.jojo.mybatis.demo.mapper
        Set<Class<?>> classes = ClassUtil.scanPackage("com.jojo.demo.mapper");
        for (Class<?> aClass : classes) {
            CacheNamespace cacheNamespace = aClass.getAnnotation(CacheNamespace.class);
            boolean isCache = cacheNamespace != null;
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                boolean isExistAnnotation = false;
                SqlCommandType sqlCommandType = null;
                // 原始SQL
                String originalSql = "";
                for (Class<? extends Annotation> sqlAnnotationType : sqlAnnotationTypeList) {
                    Annotation annotation = method.getAnnotation(sqlAnnotationType);
                    if (annotation == null) {
                        continue;
                    }
                    originalSql = (String) annotation.getClass().getMethod("value").invoke(annotation);
                    if (annotation instanceof Insert) {
                        sqlCommandType = SqlCommandType.INSERT;
                    } else if (annotation instanceof Delete) {
                        sqlCommandType = SqlCommandType.DELETE;
                    } else if (annotation instanceof Update) {
                        sqlCommandType = SqlCommandType.UPDATE;
                    } else if (annotation instanceof Select) {
                        sqlCommandType = SqlCommandType.SELECT;
                    } else {
                        throw new RuntimeException("AnnotationType is invalid. annotationType = " + annotation);
                    }
                    isExistAnnotation = true;
                }
                if (!isExistAnnotation) {
                    continue;
                }

                // 拿到mapper的返回类型
                Class returnType = null;
                boolean isSelectMany = false;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                    isSelectMany = true;
                } else if (genericReturnType instanceof Class) {
                    returnType = (Class) genericReturnType;
                }

                // 封装
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(aClass.getName() + "." + method.getName())
                        .sql(originalSql)
                        .returnType(returnType)
                        .sqlCommandType(sqlCommandType)
                        .isSelectMany(isSelectMany)
                        .cache(isCache ? new PerpetualCache(aClass.getName()) : null)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }


    }

    @SneakyThrows
    private void parseMapperXml(Configuration configuration) {
        // 解析xml
        SAXReader saxReader = new SAXReader();
        saxReader.setEntityResolver((publicId, systemId) -> new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF8'?>".getBytes())));
        BufferedInputStream inputStream = FileUtil.getInputStream(
                System.getProperty("user.dir") + "/src/main/java/com/jojo/demo/mapper/UserMapper.xml");
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//select");
        for (Element selectElement : list) {
            String methodName = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            MixedSqlNode mixedSqlNode = parseTags(selectElement);


            // 封装
            String namespace = rootElement.attributeValue("namespace");
            MappedStatement mappedStatement = MappedStatement.builder()
                    .id(namespace + "." + methodName)
                    .sql("")
                    .sqlSource(mixedSqlNode)
                    .returnType(Class.forName(resultType))
                    .sqlCommandType(SqlCommandType.SELECT)
                    .isSelectMany(false)
                    .cache(new PerpetualCache(namespace))
                    .build();
            configuration.addMappedStatement(mappedStatement);
        }
    }

    private MixedSqlNode parseTags(Element element) {
        List<SqlNode> contents = Lists.newArrayList();

        List<Node> contentList = element.content();
        for (Node node : contentList) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childNodeElement = (Element) node;
                String sqlNodeType = childNodeElement.getName();
                String test = childNodeElement.attributeValue("test");
                if ("if".equals(sqlNodeType)) {
                    contents.add(new IfSqlNode(test, parseTags(childNodeElement)));
                } else if ("choose".equals(sqlNodeType)) {
                    // contents.add(new ChooseSqlNode(test, parseTags(childNodeElement)));
                }
            } else {
                String sql = node.getText();
                if (sql.contains("${")) {
                    contents.add(new TextSqlNode(sql));
                } else {
                    contents.add(new StaticTextSqlNode(sql));
                }
            }
        }
        return new MixedSqlNode(contents);
    }
}
