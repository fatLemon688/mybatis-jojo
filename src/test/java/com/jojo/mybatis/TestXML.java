package com.jojo.mybatis;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.jojo.mybatis.scripting.IfSqlNode;
import com.jojo.mybatis.scripting.MixedSqlNode;
import com.jojo.mybatis.scripting.SqlNode;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;


// 测试
public class TestXML {
    @Test
    public void test() throws Exception {
        // 解析xml
        SAXReader saxReader = new SAXReader();
        saxReader.setEntityResolver((publicId, systemId) -> new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF8'?>".getBytes())));
        BufferedInputStream inputStream = FileUtil.getInputStream(
                "D:\\Application\\IdeaProjects\\mybatis-jojo\\src\\main\\java\\com\\jojo\\demo\\mapper\\UserMapper.xml");
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//select");
        for (Element selectElement : list) {
            String methodName = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            MixedSqlNode mixedSqlNode = parseTags(selectElement);
            System.out.println(resultType);
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
                System.out.println("类型: " + sqlNodeType);
                System.out.println("表达式: " + test);
                if ("if".equals(sqlNodeType)) {
                    contents.add(new IfSqlNode(test, parseTags(childNodeElement)));
                } else if ("choose".equals(sqlNodeType)) {
                   // contents.add(new ChooseSqlNode(test, parseTags(childNodeElement)));
                }
            } else {
                String sql = node.getText();
                if (StrUtil.isNotBlank(sql)) {
                    System.out.println("sql:" + sql.trim());
                }
            }
        }
        return new MixedSqlNode(contents);
    }
}
