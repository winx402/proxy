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
        sourceWeb.setWeb("http://www.goubanjia.com/free/");
        sourceWeb.setEntrance("http://www.goubanjia.com/free/(gngn|gnpt|gwgn|gwpt)/index.shtml/?");
        sourceWeb.setShouldVisit("^http://www.goubanjia.com/free((/gngn|/gnpt|/gwgn|/gwpt)?/index(\\d{0,})?.shtml)?$");
        sourceWeb.setLineType("table");
        sourceWeb.setIpExpression("tableIp");
        sourceWeb.setPortExpression("tablePort");
        sourceWeb.setTypeExpression("tableType");
        crawlerController.init(Lists.newArrayList(sourceWeb)).doCrawling();
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[^0-9.]");
        Pattern compile = Pattern.compile("style='display: ?none;'>.*?>");
        Pattern compile1 = Pattern.compile("(.*)class=\"port.*?>(\\d{1,}).*");
        Matcher matcher = compile.matcher("<td class=\"ip\"><div style='display:inline-block;'></div><p style='display: none;'>18</p><span>18</span><p style='display: none;'>2</p><span>2</span><span style='display:inline-block;'>.</span><p style='display: none;'>4</p><span>4</span><p style='display: none;'>2.</p><span>2.</span><span style='display:inline-block;'>39</span><span style='display:inline-block;'>.</span><span style='display:inline-block;'>20</span><span style='display:inline-block;'>3</span>:<span class=\"port DCBZGI\">8767</span></td>");
        String all = matcher.replaceAll("");
//        Matcher matcher1 = pattern.matcher(all);
//        all = matcher1.replaceAll("");
        Matcher matcher1 = compile1.matcher(all);
        if (matcher1.find()){
            System.out.println("ip:"+pattern.matcher(matcher1.group(1)).replaceAll(""));
            System.out.println("port:"+ matcher1.group(2));
        }
    }

}
