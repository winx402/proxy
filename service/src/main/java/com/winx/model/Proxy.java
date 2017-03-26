package com.winx.model;

import com.winx.enums.ProxyAvailable;
import com.winx.enums.ProxyType;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 代理ip
 */
public class Proxy {
    private String ip;
    private int port;
    private ProxyType proxyType;
    private ProxyAvailable available;
    private int score;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public ProxyAvailable getAvailable() {
        return available;
    }

    public void setAvailable(ProxyAvailable available) {
        this.available = available;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
