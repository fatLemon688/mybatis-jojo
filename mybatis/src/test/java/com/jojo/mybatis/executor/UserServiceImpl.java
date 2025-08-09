package com.jojo.mybatis.executor;

public class UserServiceImpl implements UserService {
    @Override
    public Object selectList(String name) {
        System.out.println("执行了selectList: " + name);
        return "ok";
    }

    @Override
    public Object selectOne(String name) {
        System.out.println("执行了selectOne: " + name);
        return "ok";
    }
}
