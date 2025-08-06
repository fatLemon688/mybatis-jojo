package com.jojo.mybatis.executor;

public interface UserService {
    Object selectList(String name);

    Object selectOne(String name);
}
