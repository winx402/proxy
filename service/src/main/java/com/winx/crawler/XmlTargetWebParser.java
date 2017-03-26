package com.winx.crawler;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.winx.crawler.template.TableXmlTarget;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * @author wangwenxiang
 * @create 2017-03-25.
 */
@Service
public class XmlTargetWebParser {

    private static final Logger logger = LoggerFactory.getLogger(XmlTargetWebParser.class);

    private List<ElementReader<Element, TableXmlTarget>> readers = new ArrayList<ElementReader<Element, TableXmlTarget>>(){{
        add(new EntranceReader());
        add(new ShouldVisitReader());
        add(new IpReader());
        add(new PortReader());
    }};

    private static final String XML_PATH = "/Users/wangwenxiang/ideaworkspace/proxy/service/src/main/resources/target/handmapper.xml";

    /**
     * 解析入口
     */
    public void initTargetWeb(Queue<TargetWebGetter> abstractTargetWebs) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(XML_PATH));
            Element root = document.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                TableXmlTarget parsing = parsing(element);
                if (parsing != null){
                    abstractTargetWebs.add(parsing);
                }
            }
        } catch (Exception e) {
            logger.error("parse from xml error", e);
        }
    }

    private TableXmlTarget parsing(Element element) {
        TableXmlTarget tableXmlTarget = new TableXmlTarget();
        for (ElementReader<Element, TableXmlTarget> elementReader : readers){
            boolean readSuccess = elementReader.read(element, tableXmlTarget);
            if (!readSuccess){
                logger.warn("parsing element fail,", elementReader.getInfo());
                return null;
            }
        }
        return tableXmlTarget;
    }

    /**
     * xml获取入口地址
     */
    private class EntranceReader implements ElementReader<Element,TableXmlTarget>{
        private static final String ENTRANCE = "entrance";
        public boolean read(Element element, TableXmlTarget tableXmlTarget) {
            if (element == null) return false;
            String s = element.elementText(ENTRANCE);
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.setEntrances(s);
            return true;
        }

        public String getInfo() {
            return "xml获取入口地址";
        }
    }

    /**
     * xml是否访问解析
     */
    private class ShouldVisitReader implements ElementReader<Element,TableXmlTarget>{
        private static final String SHOULDVISIT = "shouldvisit";
        public boolean read(Element element, TableXmlTarget tableXmlTarget) {
            if (element == null) return false;
            String s = element.elementText(SHOULDVISIT);
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
    private class IpReader implements ElementReader<Element, TableXmlTarget>{
        private static final String IP = "ip";
        public boolean read(Element element, TableXmlTarget tableXmlTarget) {
            if (element == null) return false;
            String s = element.elementText(IP);
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.setIpPattern(s);
            return true;
        }

        public String getInfo() {
            return "ip解析";
        }
    }

    /**
     * port解析
     */
    private class PortReader implements ElementReader<Element, TableXmlTarget>{
        private static final String PORT = "port";
        public boolean read(Element element, TableXmlTarget tableXmlTarget) {
            if (element == null) return false;
            String s = element.elementText(PORT);
            if (Strings.isNullOrEmpty(s)) return false;
            tableXmlTarget.setPortPattern(s);
            return true;
        }

        public String getInfo() {
            return "port解析";
        }
    }
}
