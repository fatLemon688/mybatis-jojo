package com.jojo.mybatis.binding;

import com.jojo.mybatis.annotations.Param;
import com.jojo.mybatis.annotations.Select;
import com.jojo.mybatis.parsing.GenericTokenParser;
import com.jojo.mybatis.parsing.ParameterMappingTokenHandler;
import com.jojo.mybatis.type.IntegerTypeHandler;
import com.jojo.mybatis.type.StringTypeHandler;
import com.jojo.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// mapper代理
public class MapperProxy implements InvocationHandler {

    private Map<Class, TypeHandler> typeHandlerMap = new HashMap<>();

    public MapperProxy() {
        this.typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        this.typeHandlerMap.put(String.class, new StringTypeHandler());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();

        // 拿到sql
        Select select = method.getAnnotation(Select.class);
        String originalSql = select.value();

        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = parser.parse(originalSql);


        // 构建sql和执行sql
        // jdbc里#{}替换规则：#{}---->?
        PreparedStatement ps = connection.prepareStatement(sql);

        //设置值
        // 获取mapper方法对应的（参数名 ---> 参数值）
        Map<String, Object> paramValueMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            String paramName = param.value();
            paramValueMap.put(paramName, args[i]);
        }
        List<String> parameterMappings = tokenHandler.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            // parameterMappings这个List:使字段顺序跟字段名绑定
            // paramValueMap:使字段名跟字段值绑定
            // 这样能使字段顺序跟字段值对应上，再使用PreparedStatement来设置值
            String columName = parameterMappings.get(i);
            Object val = paramValueMap.get(columName);
            typeHandlerMap.get(val.getClass()).setParameter(ps, i + 1, val);
        }
        ps.execute();

        // 拿到结果集
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString("name") + " -- " + rs.getInt("age"));
        }
        // 释放资源
        rs.close();
        ps.close();
        connection.close();
        return null;
    }

    @SneakyThrows
    private static Connection getConnection() {
        // 加载jdbc驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        return DriverManager
                .getConnection("jdbc:mysql://127.0.0.1:3306/mybatis-jojo?useSSL=false",
                        "root", "Hu468502553");
    }
}
