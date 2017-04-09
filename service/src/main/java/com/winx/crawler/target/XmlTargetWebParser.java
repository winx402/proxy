package com.winx.crawler.target;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winx.crawler.bean.SourceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 */
@Service
public class XmlTargetWebParser {

    private static final Logger logger = LoggerFactory.getLogger(XmlTargetWebParser.class);

    private List<ElementReader<SourceWeb, AbstractTableXmlTargetter>> readers = new ArrayList<ElementReader<SourceWeb, AbstractTableXmlTargetter>>() {{
        add(new WebReader());
        add(new EntranceReader());
        add(new ShouldVisitReader());
        add(new IpReader());
        add(new PortReader());
        add(new TypeReader());
    }};

    @Value("${handmapper.path}")
    private String xml_path;

    @PostConstruct
    public void init() {
        URLClassLoader loader = (URLClassLoader) XmlTargetWebParser.class.getClassLoader();
        URL resource = loader.getResource(xml_path);
        if (resource != null)
            xml_path = resource.getPath();
    }

    /**
     * 解析入口
     */
    public void initTargetWeb(Queue<TargetWebGetter> abstractTargetWebs, List<SourceWeb> sourceWebs) {
        try {
            for (SourceWeb source : sourceWebs) {
                AbstractTableXmlTargetter parsing = parsing(source);
                if (parsing != null) {
                    abstractTargetWebs.add(parsing);
                }
            }
        } catch (Exception e) {
            logger.error("parse from xml error", e);
        }
    }

    private AbstractTableXmlTargetter parsing(SourceWeb sourceWeb) throws Exception {
        AbstractTableXmlTargetter tableXmlTarget = parserGetter(sourceWeb);
        tableXmlTarget.setId(sourceWeb.getId());
        for (ElementReader<SourceWeb, AbstractTableXmlTargetter> elementReader : readers) {
            boolean readSuccess = elementReader.read(sourceWeb, tableXmlTarget);
            if (!readSuccess) {
                logger.warn("parsing element fail,", elementReader.getInfo());
                return null;
            }
        }
        return tableXmlTarget;
    }

    private AbstractTableXmlTargetter parserGetter(SourceWeb sourceWeb){
        return AbstractTableXmlTargetter.newInstance(sourceWeb.getLineType());
    }

    /**
     * xml是否访问解析
     */
    private class WebReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {
        private static final String WEB = "web";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) {
            if (sourceWeb == null) return false;
            String s = sourceWeb.getWeb();
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.setWeb(s);
            return true;
        }

        public String getInfo() {
            return "xml是否访问解析";
        }
    }

    /**
     * xml获取入口
     */
    private class EntranceReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {
        private static final String ENTRANCE = "entrance";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) {
            if (sourceWeb == null) return false;
            List<String> strings = Lists.newArrayList(sourceWeb.getEntrance());
            tableXmlTarget.setEntrances(strings);
            return true;
        }

        public String getInfo() {
            return "xml获取入口地址";
        }
    }

    /**
     * xml是否访问解析
     */
    private class ShouldVisitReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {
        private static final String SHOULDVISIT = "shouldvisit";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) {
            if (sourceWeb == null) return false;
            String s = sourceWeb.getShouldVisit();
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.setShouldVisitPattern(s);
            return true;
        }

        public String getInfo() {
            return "xml是否访问解析";
        }
    }

    /**
     * ip解析
     */
    private class IpReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {
        private static final String IP = "ip";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (sourceWeb == null) return false;
            String s = sourceWeb.getIpExpression();
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.getAttributeFacade().setIpProcesser(s);
            return true;
        }

        public String getInfo() {
            return "ip解析";
        }
    }

    /**
     * port解析
     */
    private class PortReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {
        private static final String PORT = "port";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (sourceWeb == null) return true;
            String s = sourceWeb.getPortExpression();
            tableXmlTarget.getAttributeFacade().setPortProcesser(s);
            return true;
        }

        public String getInfo() {
            return "port解析";
        }
    }

    private class TypeReader implements ElementReader<SourceWeb, AbstractTableXmlTargetter> {

        private static final String TYPE = "type";

        public boolean read(SourceWeb sourceWeb, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (sourceWeb == null) return true;
            String s = sourceWeb.getTypeExpression();
            tableXmlTarget.getAttributeFacade().setTypeProcesser(s);
            return true;
        }

        public String getInfo() {
            return "type解析";
        }
    }
}
