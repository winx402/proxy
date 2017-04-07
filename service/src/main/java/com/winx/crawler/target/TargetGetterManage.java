package com.winx.crawler.target;

import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Queue;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
@Service
public class TargetGetterManage {

    private Queue<TargetWebGetter> targetWebs;

    public TargetGetterManage(){
        targetWebs = Lists.newLinkedList();
    }

    /**
     * 是否访问
     * 爬取或者可以访问的页面都应该进入
     */
    public boolean shouldVisit(Page referringPage, WebURL url){
        String href = url.getURL().toLowerCase();
        for (TargetWebGetter getter : targetWebs){
            if (getter.shouldVisit(href) || getter.shouldEntrance(href)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否爬取
     * 只有爬取页面才能爬取
     */
    public TargetWebGetter getTargetFromUrl(String url){
        for (TargetWebGetter getter : targetWebs){
            if (getter.shouldVisit(url)){
                return getter;
            }
        }
        return null;
    }

    public List<String> getEntrance(){
        List<String> entrances = Lists.newArrayList();
        if (CollectionUtils.isEmpty(targetWebs)) return entrances;
        for (TargetWebGetter targetWebGetter : targetWebs){
            entrances.add(targetWebGetter.getWeb());
        }
        return entrances;
    }

    public Queue<TargetWebGetter> getTargetWebs() {
        return targetWebs;
    }
}
