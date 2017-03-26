package com.winx.crawler.template;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import com.winx.crawler.TargetWebGetter;
import com.winx.enums.ExceptionEnum;
import com.winx.enums.ProxyAvailable;
import com.winx.enums.ProxyType;
import com.winx.exception.ProcessException;
import com.winx.model.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 */
public class TableXmlTarget implements TargetWebGetter{

    private static final Logger logger = LoggerFactory.getLogger(TableXmlTarget.class);

    private static final Pattern trPattern = Pattern.compile("<tr.*?</tr>");

    /**
     * 入口
     */
    private List<String> entrances;

    public void setEntrances(String entrance){
        setEntrances(Lists.newArrayList(entrance));
    }

    public void setEntrances(List<String> entrances){
        this.entrances = entrances;
    }

    /**
     * ip页面正则
     */
    private Pattern shouldVisitPattern;

    public void setShouldVisitPattern(String shouldVisitPattern){
        this.shouldVisitPattern = Pattern.compile(shouldVisitPattern);
    }

    /**
     * ip解析
     */
    private Pattern ipPattern;

    public void setIpPattern(String ipPattern){
        this.ipPattern = Pattern.compile(ipPattern);
    }

    /**
     * 端口解析
     */
    private Pattern portPattern;

    public void setPortPattern(String portPattern){
        this.portPattern = Pattern.compile(portPattern);
    }

    public List<String> entrances() {
        return entrances;
    }

    public boolean shouldVisit(String url) {
        return shouldVisitPattern.matcher(url).matches();
    }

    public List<Proxy> FromPage(String pageHtml) {
        if (Strings.isNullOrEmpty(pageHtml)) return Lists.newArrayList();
        return getTr(pageHtml);
    }

    /**
     * 正则获取ip
     * ip获取失败时抛出异常
     */
    private String getIp(String trHtml) throws ProcessException{
        Matcher matcher = ipPattern.matcher(trHtml);
        if (matcher.find()){
            return matcher.group();
        }
        throw new ProcessException(ExceptionEnum.PROCESS_HTML_IP_EXCEPTION);
    }

    /**
     * 正则获取端口
     */
    private int getPort(String trHtml){
        try{
            Matcher matcher = portPattern.matcher(trHtml);
            if (matcher.find()){
                return Integer.parseInt(matcher.group());
            }
        }catch (Exception e){
            logger.error("TableXmlTarget process port error",e);
            return -1;
        }
        return -1;
    }


    private List<Proxy> getTr(final String pageHtml){
        return HtmlProcessor.html2List(pageHtml, new LineProcessor<List<Proxy>>() {

            List<Proxy> proxies = Lists.newArrayList();

            public boolean processLine(String line) throws IOException {
                logger.info("TableXmlTarget process html get tr:{}",line);
                Proxy proxy = new Proxy();
                try{
                    proxy.setIp(getIp(line));
                    proxy.setPort(getPort(line));
                    proxy.setProxyType(ProxyType.INITIAL);
                    proxy.setAvailable(ProxyAvailable.HTTP);
                    proxies.add(proxy);
                }catch (Exception e){
                    logger.error("TableXmlTarget process html error",e);
                    return false;
                }
                return true;
            }

            public List<Proxy> getResult() {
                return proxies;
            }
        });
    }

    private static class HtmlProcessor{
        static <T> T html2List(String html, LineProcessor<T> lineProcessor){
            logger.info("begin to process html through TableXmlTarget， html:{}", html);
            try{
                Matcher matcher = trPattern.matcher(html);
                while (matcher.find()){
                    lineProcessor.processLine(matcher.group());
                }
            }catch (Exception e){
                logger.error("html to proxy parse error",e);
            }
            return lineProcessor.getResult();
        }
    }
}
