package com.winx.crawler.bean;

/**
 * @author wangwenxiang
 * @create 2017-04-08.
 */
public class SourceWeb {

    private int id;
    private String web;
    private String entrance;
    private String shouldVisit;
    private String lineType;
    private String ipExpression;
    private String portExpression;
    private String typeExpression;
    private int cycle;
    private String lastTime;
    private int banTimes;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getShouldVisit() {
        return shouldVisit;
    }

    public void setShouldVisit(String shouldVisit) {
        this.shouldVisit = shouldVisit;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getIpExpression() {
        return ipExpression;
    }

    public void setIpExpression(String ipExpression) {
        this.ipExpression = ipExpression;
    }

    public String getPortExpression() {
        return portExpression;
    }

    public void setPortExpression(String portExpression) {
        this.portExpression = portExpression;
    }

    public String getTypeExpression() {
        return typeExpression;
    }

    public void setTypeExpression(String typeExpression) {
        this.typeExpression = typeExpression;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getBanTimes() {
        return banTimes;
    }

    public void setBanTimes(int banTimes) {
        this.banTimes = banTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
