package com.winx.crawler.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.winx.crawler.bean.SourceWeb;
import com.winx.crawler.target.TargetGetterManage;
import com.winx.dao.SourceWebDao;
import com.winx.util.CollectionUtil;
import com.winx.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangwenxiang
 * @create 2017-04-08.
 */
@Service
public class SourceWebService {

    private static final Logger logger = LoggerFactory.getLogger(SourceWebService.class);

    @Resource
    private SourceWebDao sourceWebDao;

    private final List<Integer> banStatus = Lists.newArrayList(404,503);

    @Resource
    private TargetGetterManage targetGetterManage;

    public List<SourceWeb> querySourceWebToRun() {
        List<SourceWeb> all = sourceWebDao.getAll();
        List<SourceWeb> sourceWebs = Lists.newArrayList();
        if (CollectionUtils.isEmpty(all)) {
            return sourceWebs;
        }
        for (SourceWeb sourceWeb : all) {
            if (shouldRun(sourceWeb)) {
                sourceWebs.add(sourceWeb);
            }
        }
        updateLastTime(sourceWebs);
        return sourceWebs;
    }

    private boolean shouldRun(SourceWeb sourceWeb) {
        if (sourceWeb == null) return false;
        if (Strings.isNullOrEmpty(sourceWeb.getLastTime())) return true;
        int hours = DateUtil.diffHours(sourceWeb.getLastTime());
        return hours >= ((sourceWeb.getBanTimes() + 1) * sourceWeb.getCycle());
    }

    public void updateLastTime(List<SourceWeb> sourceWebs){
        if (CollectionUtils.isEmpty(sourceWebs)) return;
        sourceWebDao.updateLastTime(sourceWebs);
    }

    public void updateBanTimes(){
        SetMultimap<Integer, Integer> webStatus = targetGetterManage.getWebStatus();
        logger.info("Crawler webStatus:{}",JSON.toJSONString(webStatus));
        List<Integer> banId = Lists.newArrayList();
        List<Integer> notBanId = Lists.newArrayList();
        for (Integer id: webStatus.keySet()){
            Set<Integer> integers = webStatus.get(id);
            if (banStatus.containsAll(integers)){
                banId.add(id);
            }else {
                notBanId.add(id);
            }
        }
        if (!CollectionUtils.isEmpty(banId)){
            sourceWebDao.updateBanId(banId);
        }
        if (!CollectionUtils.isEmpty(notBanId)){
            sourceWebDao.updateNotBanId(notBanId);
        }
    }

    public void soutIpNum(){
        Map<Integer, Integer> crawlerIpNum = targetGetterManage.getCrawlerIpNum();
        logger.info("Crawler ip num:{}", JSON.toJSONString(crawlerIpNum));
    }
}
