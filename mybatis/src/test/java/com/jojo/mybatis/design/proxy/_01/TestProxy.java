package com.jojo.mybatis.design.proxy._01;

import org.junit.Test;

// 测试代理
public class TestProxy {
    @Test
    public void test() throws Exception {
        UserProxy proxy = new UserProxy(new UserServiceImpl());
        Object list = proxy.selectList("xx");
        System.out.println(list);
    }
}
