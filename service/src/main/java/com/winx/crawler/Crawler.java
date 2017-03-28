package com.winx.crawler;

import com.alibaba.fastjson.JSON;
import com.winx.crawler.target.TargetGetterManage;
import com.winx.crawler.target.TargetWebGetter;
import com.winx.dao.ProxyIpDao;
import com.winx.model.ProxyIp;
import com.winx.util.SpringUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
public class Crawler extends WebCrawler {

    private Logger logger = LoggerFactory.getLogger(Crawler.class);

    private TargetGetterManage targetGetterManage = SpringUtil.getBean(TargetGetterManage.class);

    private ProxyIpDao proxyIpDao = SpringUtil.getBean(ProxyIpDao.class);

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return targetGetterManage.shouldVisit(referringPage, url);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        TargetWebGetter targetWenGetter = targetGetterManage.getTargetFromUrl(url);
        if (targetWenGetter == null) return;
        logger.info("visit url:{}", url);
        if (page.getParseData() instanceof HtmlParseData) { // 判断是否是html数据
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // 强制类型转换，获取html数据对象
            String html = htmlParseData.getHtml().replaceAll("\\n", ""); // 获取页面Html
            List<ProxyIp> proxies = targetWenGetter.FromPage(html);
            logger.info("get Proxies from {} , Proxies are : {}", url, JSON.toJSONString(proxies));
            proxyIpDao.insert(proxies);
            //todo proxie 持久化
        } else {
            logger.warn("this page have no html, page url : {}", url);
        }
    }
}