package com.jojo.mybatis.executor;

import com.jojo.mybatis.plugin.LimitInterceptor;
import com.jojo.mybatis.plugin.SqlInterceptor;
import org.junit.Test;

// 测试插件
public class TestPlugin {
    @Test
    public void test() throws Exception {
        LimitInterceptor limitInterceptor = new LimitInterceptor();
        Object limitPlugin = limitInterceptor.plugin(new UserServiceImpl());
        UserService plugin = new SqlInterceptor().plugin(limitPlugin);
        System.out.println(plugin.selectOne("xxx"));
    }
}
