package com.jojo.mybatis;

import ognl.Ognl;
import org.junit.Test;

import java.util.HashMap;


// 测试
public class TestOgnl {
    @Test
    public void test() throws Exception {
        Object value = Ognl.getValue("user.age >= 18", new HashMap<>() {{
            put("name", "jojo");
            put("user", new HashMap<>(){{
                put("age", 18);
                put("name", "recky");
            }});
        }});
        System.out.println(value);
    }
}
