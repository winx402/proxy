package com.winx.crawler;

import com.google.common.collect.Lists;
import com.winx.SpringBase;
import com.winx.crawler.bean.SourceWeb;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        sourceWeb.setWeb("http://www.ip3366.net/free/");
        sourceWeb.setEntrance("http://www.ip3366.net/free/\\?stype=\\d{1,}");
        sourceWeb.setShouldVisit("^http://www.ip3366.net/free/\\?((stype|page)=\\d{1,}&)?(page|stype)=\\d{1,}$");
        sourceWeb.setLineType("table");
        sourceWeb.setIpExpression("tableIp");
        sourceWeb.setPortExpression("tablePort");
        sourceWeb.setTypeExpression("tableType");
        crawlerController.init(Lists.newArrayList(sourceWeb)).doCrawling();
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^http://www.ip3366.net/free/\\?stype=\\d{1,}&page=\\d{1,}$");
        System.out.println(pattern.matcher("http://www.ip3366.net/free/?stype=3&page=1").matches());
    }

}
