package com.winx.crawler.target;

import com.winx.model.ProxyIp;

import java.util.List;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 目标页面获取器
 */
public interface TargetWebGetter {
    /**
     * 页面入口
     */
    List<String> entrances();

    /**
     * 页面链接是否访问判断
     */
    boolean shouldVisit(String url);

    List<ProxyIp> FromPage(String pageHtml);
}