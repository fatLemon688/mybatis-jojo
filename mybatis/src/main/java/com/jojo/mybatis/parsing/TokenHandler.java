package com.jojo.mybatis.parsing;

/**
 * 标记处理器
 */
public interface TokenHandler {

    /**
     * 处理标记
     * @param content 参数内容
     * @return 标记解析后的内容
     */
    String handleToken(String content);
}
