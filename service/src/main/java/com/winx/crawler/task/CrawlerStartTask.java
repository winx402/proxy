package com.winx.crawler.task;

import com.winx.crawler.CrawlerController;
import com.winx.crawler.bean.SourceWeb;
import com.winx.crawler.service.SourceWebService;
import com.winx.dao.ProxyIpDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private SourceWebService sourceWebService;

    @Resource
    private ProxyIpDao proxyIpDao;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void startCrawling() {
        logger.info("start to do crawler");
        List<SourceWeb> sourceWebs = sourceWebService.querySourceWebToRun();
        if (CollectionUtils.isEmpty(sourceWebs)) {
            logger.info("no web to crawler");
            return;
        }
        int ipCount = proxyIpDao.getIpCount();
        long l = System.currentTimeMillis();
        crawlerController.init(sourceWebs).doCrawling();
        int ipCount1 = proxyIpDao.getIpCount();
        logger.info("end do crawler, cost time : {}, new ip : {}", System.currentTimeMillis() - l, ipCount1 - ipCount);
        sourceWebService.updateBanTimes();
        sourceWebService.soutIpNum();
    }
}
