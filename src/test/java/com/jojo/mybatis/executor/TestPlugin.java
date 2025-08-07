package com.jojo.mybatis.executor;

import com.google.common.collect.Lists;
import com.jojo.mybatis.plugin.InterceptorChain;
import com.jojo.mybatis.plugin.LimitInterceptor;
import com.jojo.mybatis.plugin.SqlLogInterceptor;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

// 测试插件
public class TestPlugin {
    @Test
    public void test() throws Exception {
        UserService target = new UserServiceImpl();
        LimitInterceptor limitInterceptor = new LimitInterceptor();
        target = limitInterceptor.plugin(target);
        target = new SqlLogInterceptor().plugin(target);
        System.out.println(target.selectOne("xxx"));
    }

    @Test
    public void test2() throws Exception {
        List<Integer> unmodifiableList = Collections.unmodifiableList(Lists.newArrayList(1, 2, 3));
        unmodifiableList.remove(1);
    }

    @Test
    public void test3() throws Exception {
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(new LimitInterceptor());
        interceptorChain.addInterceptor(new SqlLogInterceptor());
        UserService target = new UserServiceImpl();
        target = (UserService) interceptorChain.pluginAll(target);
        System.out.println(target.selectOne("xxx"));
    }
}
