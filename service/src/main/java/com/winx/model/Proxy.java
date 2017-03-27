package com.winx.model;

import com.winx.enums.ProxyType;
import com.winx.enums.ProxyAvailable;

import java.io.Serializable;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 代理ip
 */
public class Proxy implements Serializable{
    private String ip;
    private int port;
    private ProxyAvailable available;
    private ProxyType proxyType;
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

    public ProxyAvailable getAvailable() {
        return available;
    }

    public void setAvailable(ProxyAvailable available) {
        this.available = available;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
