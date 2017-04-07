package com.winx.crawler.target;

import com.winx.model.ProxyIp;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 * 目标页面获取器
 */
public interface TargetWebGetter {
    /**
     * 是否访问
     */
    boolean shouldEntrance(String url);

    /**
     * 页面链接是否访问判断
     */
    boolean shouldVisit(String url);

    String getWeb();

    List<ProxyIp> FromPage(String pageHtml);
}
