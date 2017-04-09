package com.winx.crawler.target;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import com.winx.crawler.target.attrable.AbstractAttributeParser;
import com.winx.crawler.target.attrable.AttributeFacade;
import com.winx.enums.ProxyAvailable;
import com.winx.enums.ProxyType;
import com.winx.exception.ProcessException;
import com.winx.model.ProxyIp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 */
public abstract class AbstractTableXmlTargetter implements TargetWebGetter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTableXmlTargetter.class);

    /**
     * ip、port、type的门面类
     */
    private AttributeFacade attributeFacade = new AttributeFacade();

    private static Map<String, AbstractTableXmlTargetter> targetterMap = new HashMap<String, AbstractTableXmlTargetter>(){{
        this.put("table", new TableXmlTarget());
        this.put("p",new PXmlTarget());
    }};

    public AttributeFacade getAttributeFacade() {
        return attributeFacade;
    }

    public void setEntrances(String entrance) {
        setEntrances(Lists.newArrayList(entrance));
    }

    public void setEntrances(List<String> entrances) {
        List<Pattern> patterns = Lists.newArrayList();
        for (String en : entrances){
            patterns.add(Pattern.compile(en));
        }
        this.entrances = patterns;
    }

    private HtmlProcessor htmlProcessor = new HtmlProcessor();

    /**
     * 访问页面正则
     */
    private Pattern shouldVisitPattern;

    public void setShouldVisitPattern(String shouldVisitPattern) {
        this.shouldVisitPattern = Pattern.compile(shouldVisitPattern);
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 入口
     */
    private List<Pattern> entrances;

    private String web;

    public List<Pattern> entrances() {
        return entrances;
    }

    public String getWeb(){
        return web;
    }

    public void setWeb(String web){
        this.web = web;
    }

    /**
     * 是否访问并爬取
     */
    public boolean shouldVisit(String url) {
        return shouldVisitPattern.matcher(url).matches();
    }

    /**
     * 是否访问
     */
    public boolean shouldEntrance(String url){
        for (Pattern pattern: entrances){
            if (pattern.matcher(url).matches()){
                return true;
            }
        }
        return false;
    }

    public List<ProxyIp> FromPage(String pageHtml) {
        if (Strings.isNullOrEmpty(pageHtml)) return Lists.newArrayList();
        return getTr(pageHtml);
    }

    protected abstract Pattern getLinePattern();

    /**
     * 正则获取ip
     * ip获取失败时抛出异常
     */
    private String getIp(String trHtml) throws ProcessException {
        return attributeFacade.getIp(trHtml);
    }

    /**
     * 正则获取端口
     */
    private int getPort(String trHtml) {
        return attributeFacade.getPort(trHtml);
    }

    /**
     * 获取代理类型
     */
    private ProxyType getType(String trHtml) {
        return attributeFacade.getType(trHtml);
    }


    private List<ProxyIp> getTr(final String pageHtml) {
        return htmlProcessor.html2List(pageHtml, new LineProcessor<List<ProxyIp>>() {

            List<ProxyIp> proxies = Lists.newArrayList();

            public boolean processLine(String line) throws IOException {
                logger.info("TableXmlTarget process html get tr:{}", line);
                ProxyIp proxy = new ProxyIp();
                try {
                    proxy.setIp(getIp(line));
                    proxy.setPort(getPort(line));
                    proxy.setProxyType(getType(line));
                    proxy.setAvailable(ProxyAvailable.INITIAL);
                    proxy.setCreateTime(new DateTime().toString("yyyy-MM-dd"));
                    proxies.add(proxy);
                } catch (Exception e) {
                    logger.error("TableXmlTarget process html error, html : {}", line);
                    return false;
                }
                return true;
            }

            public List<ProxyIp> getResult() {
                return proxies;
            }
        });
    }

    private class HtmlProcessor {
        public <T> T html2List(String html, LineProcessor<T> lineProcessor) {
            logger.info("begin to process html through TableXmlTarget， html:{}", html);
            try {
                Pattern linePattern = getLinePattern();
                Matcher matcher = linePattern.matcher(html);
                while (matcher.find()) {
                    lineProcessor.processLine(matcher.group());
                }
            } catch (Exception e) {
                logger.error("html to proxy parse error,html:{},message:{}", html, e.getMessage());
            }
            return lineProcessor.getResult();
        }
    }

    public static AbstractTableXmlTargetter newInstance(String type){
        return targetterMap.get(type);
    }

    private static class TableXmlTarget extends AbstractTableXmlTargetter {
        private static final Pattern linePattern = Pattern.compile("<tr.*?</tr>");

        @Override
        protected Pattern getLinePattern() {
            return linePattern;
        }
    }

    private static class PXmlTarget extends AbstractTableXmlTargetter {
        private static final Pattern linePattern = Pattern.compile("<p.*?</p>");

        @Override
        protected Pattern getLinePattern() {
            return linePattern;
        }
    }
}
