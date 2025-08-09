package com.jojo.mybatis;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
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
            List<Node> contentList = selectElement.content();
            for (Node node : contentList) {
                parseTags(node);
            }
            System.out.println(resultType);
        }
    }

    private void parseTags(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element childNodeElement = (Element) node;
            String sqlNodeType = childNodeElement.getName();
            String test = childNodeElement.attributeValue("test");
            System.out.println("类型: " + sqlNodeType);
            System.out.println("表达式: " + test);
            List<Node> contentList = childNodeElement.content();
            contentList.forEach(item -> parseTags(item));
        } else {
            String sql = node.getText();
            if (StrUtil.isNotBlank(sql)) {
                System.out.println("sql:" + sql.trim());
            }
        }
    }
}
