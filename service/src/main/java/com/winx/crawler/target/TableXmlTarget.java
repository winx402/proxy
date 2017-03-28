package com.winx.crawler.target;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import com.winx.enums.ExceptionEnum;
import com.winx.enums.ProxyType;
import com.winx.enums.ProxyAvailable;
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
public class TableXmlTarget implements TargetWebGetter{

    private static final Logger logger = LoggerFactory.getLogger(TableXmlTarget.class);

    private static final Pattern trPattern = Pattern.compile("<tr.*?</tr>");

    private static final Map<String, Pattern> ipPatternMap = new HashMap<String, Pattern>(){{
        put("tableIp",Pattern.compile("<td>(\\d+\\.\\d+\\.\\d+\\.\\d+)</td>"));
    }};

    private static final Map<String, Pattern> portPatternMap = new HashMap<String, Pattern>(){{
        put("tablePort", Pattern.compile("<td>([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])</td>"));
    }};
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
     * 访问页面正则
     */
    private Pattern shouldVisitPattern;

    public void setShouldVisitPattern(String shouldVisitPattern){
        this.shouldVisitPattern = Pattern.compile(shouldVisitPattern);
    }

    /**
     * ip解析
     */
    private Pattern ipPattern;

    public void setIpPattern(String ipType) throws ProcessException{
        this.ipPattern = ipPatternMap.get(ipType);
        if (this.ipPattern == null){
            throw new ProcessException(ExceptionEnum.NOT_FIND_IP_PATTERN);
        }
    }

    /**
     * 端口解析
     */
    private Pattern portPattern;

    public void setPortPattern(String portType) throws ProcessException{
        this.portPattern = portPatternMap.get(portType);
        if (this.portPattern == null){
            throw new ProcessException(ExceptionEnum.NOT_FIND_PORT_PATTERN);
        }
    }

    public List<String> entrances() {
        return entrances;
    }

    public boolean shouldVisit(String url) {
        return shouldVisitPattern.matcher(url).matches();
    }

    public List<ProxyIp> FromPage(String pageHtml) {
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
            return matcher.group(1);
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
                return Integer.parseInt(matcher.group(1));
            }
        }catch (Exception e){
            logger.error("TableXmlTarget process port error",e);
            return -1;
        }
        return -1;
    }


    private List<ProxyIp> getTr(final String pageHtml){
        return HtmlProcessor.html2List(pageHtml, new LineProcessor<List<ProxyIp>>() {

            List<ProxyIp> proxies = Lists.newArrayList();

            public boolean processLine(String line) throws IOException {
                logger.info("TableXmlTarget process html get tr:{}",line);
                ProxyIp proxy = new ProxyIp();
                try{
                    proxy.setIp(getIp(line));
                    proxy.setPort(getPort(line));
                    proxy.setProxyType(ProxyType.HTTP);
                    proxy.setAvailable(ProxyAvailable.INITIAL);
                    proxy.setCreateTime(new DateTime().toString("yyyy-MM-dd"));
                    proxies.add(proxy);
                }catch (Exception e){
                    logger.error("TableXmlTarget process html error",e);
                    return false;
                }
                return true;
            }

            public List<ProxyIp> getResult() {
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
