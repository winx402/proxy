package com.winx.crawler;

import com.winx.SpringBase;
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
        crawlerController.doCrawling();
    }
}
