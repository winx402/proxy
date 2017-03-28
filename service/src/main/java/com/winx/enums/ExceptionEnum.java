package com.winx.enums;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
public enum ExceptionEnum {
    PROCESS_HTML_IP_EXCEPTION("html解析ip错误"),
    NOT_FIND_IP_PATTERN("没有找到ip解析器"),
    NOT_FIND_PORT_PATTERN("没有找到port解析器"),
    NOT_FIND_TYPE_PATTERN("没有找到type解析器");

    private String decs;

    ExceptionEnum(String decs) {
        this.decs = decs;
    }
}
