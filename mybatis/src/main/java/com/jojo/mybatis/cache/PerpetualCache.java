package com.jojo.mybatis.cache;

import java.util.HashMap;
import java.util.Map;

/**
 *  一级缓存，永久缓存
 */
public class PerpetualCache implements Cache {
    // 缓存Id
    private String id;

    private Map<Object, Object> cacheMap = new HashMap<>();

    public PerpetualCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cacheMap.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cacheMap.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cacheMap.remove(key);
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }
}
