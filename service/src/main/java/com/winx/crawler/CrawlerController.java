package com.winx.crawler;

import com.alibaba.fastjson.JSON;
import com.winx.crawler.target.TargetGetterManage;
import com.winx.crawler.target.XmlTargetWebParser;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TargetGetterManage targetGetterManage;

    private static final int numberOfCrawlers = 3;

    private static CrawlerFactory crawlerFactory;

    private volatile boolean initialized = false;

    /**
     * init
     */
    @PostConstruct
    public void init() {
        logger.info("init CrawlerController");
        try {
            xmlTargetWebParser.initTargetWeb(targetGetterManage.getTargetWebs());
            crawlerFactory = new CrawlerFactory();
            initialized = true;
        } catch (Exception e) {
            logger.error("init CrawlerController error", e);
            initialized = false;
        }

    }

    /**
     * 执行
     */
    public void doCrawling() {
        if (!initialized) {
            logger.info("CrawlerController not initialized");
            return;
        }
        if (targetGetterManage == null) {
            logger.error("targetGetterManage is null");
            return;
        }
        List<String> entrances = targetGetterManage.getEntrance();
        try {
            CrawlController controller = crawlerFactory.newCrawlController();
            for (String entrance : entrances) {
                controller.addSeed(entrance);
            }
            controller.start(Crawler.class, numberOfCrawlers);
        } catch (Exception e) {
            logger.error("run crawler error, entrances : {}", JSON.toJSONString(entrances), e);
        }
    }

    public class CrawlerFactory {
        private static final String crawlStorageFolder = "data/crawl/root";

        private CrawlController newCrawlController() throws Exception {
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setPolitenessDelay(1000); //1000毫秒的间隔
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            return new CrawlController(config, pageFetcher, robotstxtServer);
        }
    }
}
