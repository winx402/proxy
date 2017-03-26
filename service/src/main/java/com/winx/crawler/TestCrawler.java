package com.winx.crawler;

import com.alibaba.druid.support.json.JSONUtils;
import com.winx.model.Proxy;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
public class TestCrawler extends WebCrawler {

    private Logger logger = LoggerFactory.getLogger(TestCrawler.class);

    private final static Pattern filters = Pattern.compile("^http://www.xicidaili.com/nn/1$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return CrawlerController.getTargetWenGetter().shouldVisit(href);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("visit url:{}", url);
        if (page.getParseData() instanceof HtmlParseData) { // 判断是否是html数据
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // 强制类型转换，获取html数据对象
            String html = htmlParseData.getHtml().replaceAll("\\n", ""); // 获取页面Html
            TargetWebGetter targetWenGetter = CrawlerController.getTargetWenGetter();
            List<Proxy> proxies = targetWenGetter.FromPage(html);
            logger.info("get Proxies from {} , Proxies are : ",url, JSONUtils.toJSONString(proxies));
            //todo proxie 持久化
        }else {
            logger.warn("this page have no html, page url : {}", url);
        }
    }
}
