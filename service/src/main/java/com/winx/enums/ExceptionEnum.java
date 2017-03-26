package com.winx.enums;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
public enum ExceptionEnum {
    PROCESS_HTML_IP_EXCEPTION("html解析ip错误");

    private String decs;

    ExceptionEnum(String decs) {
        this.decs = decs;
    }
}
