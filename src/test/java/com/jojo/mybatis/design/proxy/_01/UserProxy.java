package com.jojo.mybatis.design.proxy._01;

// 静态代理
public class UserProxy implements UserService{
    private UserService userService;

    public UserProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object selectList(String name) {
        System.out.println("代理类执行了---start");
        Object list = userService.selectList(name);
        System.out.println("代理类执行完---end");
        return list;
    }

    @Override
    public Object selectOne(String name) {
        System.out.println("代理类执行了---start");
        Object one = userService.selectOne(name);
        System.out.println("代理类执行完---end");
        return one;
    }
}
