package com.winx.crawler;

import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 * 爬虫控制器
 */
@Service
public class CrawlerController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    @Resource
    private XmlTargetWebParser xmlTargetWebParser;

    private Queue<TargetWebGetter> targetWebs;

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private static final int numberOfCrawlers = 5;

    private static CrawlController controller;

    private static ThreadLocal<TargetWebGetter> webGetterThreadLocal = new ThreadLocal<TargetWebGetter>();

    public static TargetWebGetter getTargetWenGetter() {
        return webGetterThreadLocal.get();
    }

    /**
     * 初始化
     */
    private void init() {
        logger.info("init CrawlerController");
        targetWebs = Lists.newLinkedList();
        try {
            xmlTargetWebParser.initTargetWeb(targetWebs);
            initCraw();
        } catch (Exception e) {
            logger.error("init CrawlerController error", e);
        }

    }

    /**
     * 执行
     * 通过ThreadLocal传递TargetWebGetter
     */
    public void doCrawling() {
        for (final TargetWebGetter targetWebGetter : targetWebs) {
            singleThreadExecutor.submit(new Runnable() {
                public void run() {
                    try {
                        for (String entrance : targetWebGetter.entrances()) {
                            controller.addSeed(entrance);
                        }
                        webGetterThreadLocal.set(targetWebGetter);
                        controller.start(TestCrawler.class, numberOfCrawlers);
                    } catch (Exception e) {
                        logger.error("run crawler error", e);
                    }finally {
                        webGetterThreadLocal.remove();
                    }
                }
            });
        }
    }

    private void initCraw() throws Exception {
        CrawlConfig config = new CrawlConfig();
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);
    }
}
