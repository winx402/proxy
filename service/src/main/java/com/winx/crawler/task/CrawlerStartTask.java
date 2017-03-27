package com.winx.crawler.task;

import com.winx.crawler.CrawlerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 * 爬虫定时器
 */
@Component
public class CrawlerStartTask {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerStartTask.class);

    @Resource
    private CrawlerController crawlerController;

    @Scheduled(cron = "0 52 23 * * ?")
    public void startCrawling() {
        logger.info("start to do crawler");
        long l = System.currentTimeMillis();
        crawlerController.doCrawling();
        logger.info("end do crawler, cost time : {}", System.currentTimeMillis() - l);
    }
}
