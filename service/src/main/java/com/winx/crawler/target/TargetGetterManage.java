package com.winx.crawler.target;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
@Service
public class TargetGetterManage {

    private static final Logger logger = LoggerFactory.getLogger(TargetGetterManage.class);

    private Queue<TargetWebGetter> targetWebs;

    private SetMultimap<Integer, Integer> webStatus;

    private Map<Integer, Integer> crawlerIpNum;

    public TargetGetterManage() {
        init();
    }

    public void init() {
        targetWebs = Lists.newLinkedList();
        webStatus = TreeMultimap.create();
        crawlerIpNum = Maps.newHashMap();
    }

    /**
     * 是否访问
     * 爬取或者可以访问的页面都应该进入
     */
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        for (TargetWebGetter getter : targetWebs) {
            if (getter.shouldVisit(href) || getter.shouldEntrance(href)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否爬取
     * 只有爬取页面才能爬取
     */
    public TargetWebGetter getTargetFromUrl(String url) {
        for (TargetWebGetter getter : targetWebs) {
            if (getter.shouldVisit(url)) {
                return getter;
            }
        }
        return null;
    }

    /**
     * 获取网站入口
     */
    public List<String> getWeb() {
        List<String> webs = Lists.newArrayList();
        if (CollectionUtils.isEmpty(targetWebs)) return webs;
        for (TargetWebGetter targetWebGetter : targetWebs) {
            webs.add(targetWebGetter.getWeb());
        }
        return webs;
    }

    /**
     * 记录每个网站的访问状态
     */
    public void putStatus(String url, int status) {
        for (TargetWebGetter getter : targetWebs) {
            if (getter.getWeb().equalsIgnoreCase(url) || getter.shouldVisit(url) || getter.shouldEntrance(url)) {
                webStatus.put(getter.getId(), status);
                return;
            }
        }
        logger.info("{} not find id, status is {}", url, status);
    }

    /**
     * 统计每个网站的ip爬取数量(不一定是有效数量)
     */
    public void putIpNum(String url, int num) {
        for (TargetWebGetter getter : targetWebs) {
            if (getter.getWeb().equalsIgnoreCase(url) || getter.shouldVisit(url) || getter.shouldEntrance(url)) {
                Integer integer = crawlerIpNum.get(getter.getId());
                if (integer == null){
                    integer = 0;
                }
                integer += num;
                crawlerIpNum.put(getter.getId(), integer);
                return;
            }
        }
    }

    public Queue<TargetWebGetter> getTargetWebs() {
        return targetWebs;
    }

    public SetMultimap<Integer, Integer> getWebStatus() {
        return webStatus;
    }

    public Map<Integer, Integer> getCrawlerIpNum() {
        return crawlerIpNum;
    }
}
