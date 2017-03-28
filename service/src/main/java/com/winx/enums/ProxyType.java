package com.winx.enums;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 代理类型
 */
public enum ProxyType {
    NONE(0),HTTP(1);

    private int code;

    ProxyType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ProxyType fromCode(int code){
        for (ProxyType proxyType : ProxyType.values()){
            if (proxyType.getCode() == code){
                return proxyType;
            }
        }
        return NONE;
    }
}
