package com.winx.crawler.target.attrable;

import com.winx.enums.ProxyType;
import com.winx.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.winx.enums.ExceptionEnum.*;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 * attributeParser 管理类。
 */
public class AttributeFacade {

    private static final Logger logger = LoggerFactory.getLogger(AttributeFacade.class);

    private static final String IP = "ip";
    private static final String PORT = "port";
    private static final String TYPE = "type";

    /**
     * ip解析
     */
    private AttributeProcesser<String> ipProcesser;

    /**
     * 端口解析
     */
    private AttributeProcesser<Integer> portProcesser;

    /**
     * 代理类型解析
     */
    private AttributeProcesser<ProxyType> typeProcesser;


    private static final Map<String, AbstractAttributeParser> parserMap = new HashMap<String, AbstractAttributeParser>() {{
        put(IP, new IpAttributeParser());
        put(PORT, new PortAttributeParser());
        put(TYPE, new TypeAttributeParser());
    }};

    public void setIpProcesser(String ipKey) throws Exception {
        IpAttributeParser ipAttributeParser = (IpAttributeParser) parserMap.get(IP);
        AttributeProcesser<String> ipProcesser = ipAttributeParser.getProcesserFromKey(ipKey);
        if (ipProcesser == null) throw new ProcessException(NOT_FIND_IP_PATTERN);
        this.ipProcesser = ipProcesser;
    }

    public void setPortProcesser(String portKey) throws Exception {
        PortAttributeParser portAttributeParser = (PortAttributeParser) parserMap.get(PORT);
        AttributeProcesser<Integer> portProcesser = portAttributeParser.getProcesserFromKey(portKey);
        if (portProcesser == null) throw new ProcessException(NOT_FIND_PORT_PATTERN);
        this.portProcesser = portProcesser;
    }

    public void setTypeProcesser(String typeKey) throws Exception {
        TypeAttributeParser typeAttributeParser = (TypeAttributeParser) parserMap.get(TYPE);
        AttributeProcesser<ProxyType> typeProcesser = typeAttributeParser.getProcesserFromKey(typeKey);
        if (typeProcesser == null) throw new ProcessException(NOT_FIND_TYPE_PATTERN);
        this.typeProcesser = typeProcesser;
    }

    public String getIp(String source) throws ProcessException {
        return ipProcesser.getAttrable(source);
    }

    public Integer getPort(String source) {
        try {
            return portProcesser.getAttrable(source);
        } catch (ProcessException e) {
            logger.info("never here");
            return -1;
        }
    }

    public ProxyType getType(String source) {
        try {
            return typeProcesser.getAttrable(source);
        } catch (ProcessException e) {
            logger.info("never here");
            return ProxyType.NONE;
        }
    }
}
