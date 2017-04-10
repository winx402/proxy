package com.winx.crawler.target.attrable;

import com.winx.enums.ProxyType;
import com.winx.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public class TypeAttributeParser extends AbstractAttributeParser<ProxyType> {

    private static final Logger logger = LoggerFactory.getLogger(TypeAttributeParser.class);

    private static final String TABLE_TYPE_PATTERN = "tableType";
    private static final String P_TYPE_PATTERN = "pType";
    private static final String NONE = "none";

    protected TypeAttributeParser() {
        super(new HashMap<String, AttributeProcesser<ProxyType>>() {{
            this.put(TABLE_TYPE_PATTERN, new TableTypePattern());
            this.put(P_TYPE_PATTERN, new PTypePattern());
            this.put(NONE, new NoneType());
        }});
    }

    public static abstract class AbstractTypePattern implements AttributeProcesser<ProxyType> {
        public ProxyType getAttrable(String source) throws ProcessException {
            Matcher matcher = getPattern().matcher(source);
            if (matcher.find()) {
                ProxyType proxyType = ProxyType.fromString(matcher.group(1));
                if (proxyType == ProxyType.NONE) {
                    logger.error("this type not parser success !,{}", source);
                }
                return proxyType;
            }
            logger.error("this type not parser success !!,{}", source);
            return ProxyType.NONE;
        }

        protected abstract Pattern getPattern();
    }

    private static class TableTypePattern extends AbstractTypePattern {
        private static final Pattern typePattern = Pattern.compile("<td>(http|HTTP|https|HTTPS|socket4|SOCKET4|socket5|SOCKET5|socks4/5|SOCKS4/5)</td>");

        protected Pattern getPattern() {
            return typePattern;
        }
    }

    private static class PTypePattern extends AbstractTypePattern {
        private static final Pattern typePattern = Pattern.compile("<p>.*@(.*)#.*</p>");

        protected Pattern getPattern() {
            return typePattern;
        }
    }

    private static class NoneType extends AbstractTypePattern {
        @Override
        public ProxyType getAttrable(String source) throws ProcessException {
            return ProxyType.NONE;
        }

        protected Pattern getPattern() {
            return null;
        }
    }
}
