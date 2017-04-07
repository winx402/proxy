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

    private static final String TABLE_TYPE_PATTERN = "tableTypePattern";

    protected TypeAttributeParser() {
        super(new HashMap<String, AttributeProcesser<ProxyType>>() {{
            this.put(TABLE_TYPE_PATTERN, new TableTypePattern());
        }});
    }

    private static class TableTypePattern implements AttributeProcesser<ProxyType> {
        private static final Pattern typePattern = Pattern.compile("<td>(http|HTTP|https|HTTPS|socket4|SOCKET4|socket5|SOCKET5|socks4/5|SOCKS4/5)</td>");

        public ProxyType getAttrable(String source) throws ProcessException {
            Matcher matcher = typePattern.matcher(source);
            if (matcher.find()) {
                return ProxyType.fromString(matcher.group(1));
            }
            logger.error("this type not parser success,{}", source);
            return ProxyType.NONE;
        }
    }
}
