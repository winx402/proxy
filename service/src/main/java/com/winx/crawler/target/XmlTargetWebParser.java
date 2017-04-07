package com.winx.crawler.target;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winx.exception.ProcessException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
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

    private List<ElementReader<Element, AbstractTableXmlTargetter>> readers = new ArrayList<ElementReader<Element, AbstractTableXmlTargetter>>() {{
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
    public void initTargetWeb(Queue<TargetWebGetter> abstractTargetWebs) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(xml_path));
            Element root = document.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                AbstractTableXmlTargetter parsing = parsing(element);
                if (parsing != null) {
                    abstractTargetWebs.add(parsing);
                }
            }
        } catch (Exception e) {
            logger.error("parse from xml error", e);
        }
    }

    private AbstractTableXmlTargetter parsing(Element element) throws Exception {
        AbstractTableXmlTargetter tableXmlTarget = parserGetter(element);
        for (ElementReader<Element, AbstractTableXmlTargetter> elementReader : readers) {
            boolean readSuccess = elementReader.read(element, tableXmlTarget);
            if (!readSuccess) {
                logger.warn("parsing element fail,", elementReader.getInfo());
                return null;
            }
        }
        return tableXmlTarget;
    }

    private AbstractTableXmlTargetter parserGetter(Element element){
        String lineType = element.elementText("lineType");
        return AbstractTableXmlTargetter.newInstance(lineType);
    }

    /**
     * xml是否访问解析
     */
    private class WebReader implements ElementReader<Element, AbstractTableXmlTargetter> {
        private static final String WEB = "web";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) {
            if (element == null) return false;
            String s = element.elementText(WEB);
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
    private class EntranceReader implements ElementReader<Element, AbstractTableXmlTargetter> {
        private static final String ENTRANCE = "entrance";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) {
            if (element == null) return false;
            List list = element.elements(ENTRANCE);
            if (CollectionUtils.isEmpty(list)) return false;
            List<String> strings = Lists.newArrayList();
            for (Object o : list){
                if (o instanceof DefaultElement){
                    strings.add(((DefaultElement) o).getText());
                }
            }
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
    private class ShouldVisitReader implements ElementReader<Element, AbstractTableXmlTargetter> {
        private static final String SHOULDVISIT = "shouldvisit";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) {
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
    private class IpReader implements ElementReader<Element, AbstractTableXmlTargetter> {
        private static final String IP = "ip";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (element == null) return false;
            String s = element.elementText(IP);
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
    private class PortReader implements ElementReader<Element, AbstractTableXmlTargetter> {
        private static final String PORT = "port";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (element == null) return true;
            String s = element.elementText(PORT);
            tableXmlTarget.getAttributeFacade().setPortProcesser(s);
            return true;
        }

        public String getInfo() {
            return "port解析";
        }
    }

    private class TypeReader implements ElementReader<Element, AbstractTableXmlTargetter> {

        private static final String TYPE = "type";

        public boolean read(Element element, AbstractTableXmlTargetter tableXmlTarget) throws Exception {
            if (element == null) return true;
            String s = element.elementText(TYPE);
            tableXmlTarget.getAttributeFacade().setTypeProcesser(s);
            return true;
        }

        public String getInfo() {
            return "type解析";
        }
    }
}
