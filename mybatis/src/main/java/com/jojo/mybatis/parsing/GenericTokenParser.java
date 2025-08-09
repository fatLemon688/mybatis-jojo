package com.jojo.mybatis.parsing;

import cn.hutool.core.util.StrUtil;

import java.util.List;

// 解析sql
// 把select * from t_user where id = #{id} and name = #{name}
// 转成select * from t_user where id = ? and name = ?
public class GenericTokenParser {
    // 开始标志---#{
    private String openToken;
    // 结束标志---}
    private String closeToken;

    private TokenHandler tokenHandler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler tokenHandler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.tokenHandler = tokenHandler;
    }

    public String parse(String text) {
        if (StrUtil.isBlank(text)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        char[] charArray = text.toCharArray();
        int offset = 0;
        // select * from t_user where id = #{id} and name = #{name}

        // start所指 ----》 select * from t_user where id =
        int start = text.indexOf(openToken);
        // 三指针start,end,offset
        // start定位openToken索引
        // end定位closeToken索引
        // offset决定builder要从哪里开始append
        while (start > -1) {
            int end = text.indexOf(closeToken, start);
            if (end <= -1) {
                result.append(charArray, offset, text.length() - offset);
                offset = text.length();
            }
            if (end > -1) {
                result.append(charArray, offset, start - offset);
                offset = start + openToken.length();
                String param = new String(charArray, offset, end - offset);
                result.append(tokenHandler.handleToken(param));
                offset = end + closeToken.length();
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < text.length()) {
            result.append(charArray, offset, text.length() - offset);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String result = parser.parse("select * from t_user where id = #{id} and name = #{name}");
        System.out.println(result);

        List<String> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        System.out.println(parameterMappings);
    }
}
