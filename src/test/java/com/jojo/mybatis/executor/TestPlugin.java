package com.jojo.mybatis.executor;

import com.google.common.collect.Lists;
import com.jojo.mybatis.plugin.LimitInterceptor;
import com.jojo.mybatis.plugin.Plugin;
import com.jojo.mybatis.plugin.SqlInterceptor;
import org.junit.Test;

// 测试插件
public class TestPlugin {
    @Test
    public void test() throws Exception {
        UserService warp = Plugin.warp(new UserServiceImpl(), new LimitInterceptor());
        UserService warp2 = Plugin.warp(warp, new SqlInterceptor());
        System.out.println(warp2.selectOne("xxx"));
    }
}
