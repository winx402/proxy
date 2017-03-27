package com.winx.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 * servlet 初始化 调用crawlerController
 */
public class CrawlerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("#####contextInitialized");
//        CrawlerController bean = SpringUtil.getBean(CrawlerController.class);
//        bean.init();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
