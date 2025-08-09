package com.jojo.mybatis.plugin;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 *  拦截器责任链
 */
public class InterceptorChain {
    List<Interceptor> interceptorList = Lists.newArrayList();

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptorList) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public List<Interceptor> getInterceptorList() {
        return Collections.unmodifiableList(interceptorList);
    }
}
