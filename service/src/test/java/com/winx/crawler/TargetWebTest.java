package com.winx.crawler;

import com.google.common.collect.Lists;
import com.winx.SpringBase;
import com.winx.crawler.bean.SourceWeb;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author wangwenxiang
 * @create 2017-03-27.
 */
public class TargetWebTest extends SpringBase{

    private Logger logger = LoggerFactory.getLogger(TargetWebTest.class);

    @Resource
    private CrawlerController crawlerController;

    @Test
    public void test() {
        logger.info("********** start test **********");
        SourceWeb sourceWeb = new SourceWeb();
        sourceWeb.setWeb("http://www.youdaili.net");
        sourceWeb.setEntrance("http://www.youdaili.net/daili/(http|guonei|qq|socks|guowai|area)/?");
        sourceWeb.setShouldVisit("^http://www.youdaili.net/daili/(http|guonei|qq|socks|guowai|area)/\\d{1,}.html$");
        sourceWeb.setLineType("p");
        sourceWeb.setIpExpression("pIp");
        sourceWeb.setPortExpression("pPort");
        sourceWeb.setTypeExpression("pType");
        crawlerController.init(Lists.newArrayList(sourceWeb)).doCrawling();
    }
}
