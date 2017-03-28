package com.winx.enums;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 可用状态
 */
public enum ProxyAvailable {
    /**
     * USABLE
     * 可用状态
     */
    USEABLE(10),
    /**
     * 不可用状态
     */
    DISABLE(0),
    /**
     * 初始状态
     */
    INITIAL(-1);

    private int code;

    ProxyAvailable(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ProxyAvailable fromCode(int code){
        for (ProxyAvailable proxyAvailable : ProxyAvailable.values()){
            if (proxyAvailable.getCode() == code){
                return proxyAvailable;
            }
        }
        return INITIAL;
    }


}
