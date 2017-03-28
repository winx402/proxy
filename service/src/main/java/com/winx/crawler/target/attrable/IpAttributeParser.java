package com.winx.crawler.target.attrable;


import com.winx.enums.ExceptionEnum;
import com.winx.exception.ProcessException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public class IpAttributeParser extends AbstractAttributeParser<String> {

    private static final String TABLE_IP_PATTERN = "tableIpPattern";

    protected IpAttributeParser() {
        super(new HashMap<String, AttributeProcesser<String>>() {{
            this.put(TABLE_IP_PATTERN, new TableIpPattern());
        }});
    }

    private static class TableIpPattern implements AttributeProcesser<String> {
        private static final Pattern ipPattern = Pattern.compile("<td>(\\d+\\.\\d+\\.\\d+\\.\\d+)</td>");

        public String getAttrable(String source) throws ProcessException {
            Matcher matcher = ipPattern.matcher(source);
            if (matcher.find()) {
                return matcher.group(1);
            }
            throw new ProcessException(ExceptionEnum.PROCESS_HTML_IP_EXCEPTION);
        }
    }
}
