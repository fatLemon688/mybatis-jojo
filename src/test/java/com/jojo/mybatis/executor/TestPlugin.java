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
        UserService warp = Plugin.warp(new UserServiceImpl(), Lists.newArrayList(
                new LimitInterceptor(),
                new SqlInterceptor()
        ));
        System.out.println(warp.selectOne("xxx"));
    }
}
