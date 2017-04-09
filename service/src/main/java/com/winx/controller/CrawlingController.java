package com.winx.controller;

import com.winx.crawler.CrawlerController;
import com.winx.crawler.task.CrawlerStartTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wangwenxiang
 * @create 2017-03-29.
 */
@Controller
@RequestMapping("crawler")
public class CrawlingController {

    private static final Logger log = LoggerFactory.getLogger(CrawlingController.class);

    @Resource
    private CrawlerStartTask crawlerStartTask;

    @RequestMapping("doCrawling")
    @ResponseBody
    public String doCrawlerNow(){
        log.info("start crawling by controller");
        long l = System.currentTimeMillis();
        crawlerStartTask.startCrawling();
        long l1 = System.currentTimeMillis() - l;
        log.info("crawling complete ,cost time : {}", l1);
        return l1+"";
    }
}
