package com.jojo.mybatis;

import cn.hutool.core.util.ReflectUtil;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


// 测试反射
public class TestReflect {
    @Test
    public void test() throws Exception {
        Class<User> userClass = User.class;
        // public修饰的字段
        Field nameField = userClass.getField("name");
        System.out.println(nameField);

        User user = userClass.newInstance();
        nameField.set(user, "jojo");
        System.out.println("nameField: " + nameField.get(user));

        // private修饰的字段
        Field ageField = userClass.getDeclaredField("age");
        System.out.println(ageField);
        ageField.set(user, 18);
        System.out.println("ageField: " + ageField.get(user));

        Object hello = userClass.getMethod("hello").invoke(user);
        System.out.println("hello方法返回值: " + hello);

        Object eat = userClass.getDeclaredMethod("eat", String.class).invoke(user, "jojo");
        System.out.println(eat);

        Object eat2 = userClass.getDeclaredMethod("eat2").invoke(user);
        System.out.println(eat2);
    }

    @Test
    public void test2() throws Exception {
        Field nameField = ReflectUtil.getField(User.class, "name");
        System.out.println("nameField: " + nameField);
        Method[] methods = ReflectUtil.getMethods(User.class);
        System.out.println(methods.length);
    }

    @Data
    static class User {
        public String name;
        private Integer age;

        public String hello() {
            System.out.println("hello...");
            return "hi";
        }

        private String eat(String name) {
            System.out.println("eat...");
            return name + "吃饭了...";
        }

        private String eat2() {
            System.out.println("eat2...");
            return "吃饭了...";
        }
    }
}
