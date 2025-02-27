package com.jojo.mybatis.design.proxy._01;

public class UserServiceImpl implements UserService{
    @Override
    public Object selectList(String name) {
        System.out.println("执行了查询方法: " + name);
        return "ok";
    }

    @Override
    public Object selectOne(String name) {
        System.out.println("执行了查询方法: " + name);
        return "ok";
    }
}
