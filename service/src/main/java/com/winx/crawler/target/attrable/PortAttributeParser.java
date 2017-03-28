package com.winx.crawler.target.attrable;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public class PortAttributeParser extends AbstractAttributeParser<Integer> {

    private static final String TABLE_PORT_PATTERN = "tablePortPattern";

    protected PortAttributeParser() {
        super(new HashMap<String, AttributeProcesser<Integer>>() {{
            this.put(TABLE_PORT_PATTERN, new TablePortPattern());
        }});
    }

    private static class TablePortPattern implements AttributeProcesser<Integer> {
        private static final Pattern ipPattern = Pattern.compile("<td>([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])</td>");

        public Integer getAttrable(String source) {
            Matcher matcher = ipPattern.matcher(source);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            return -1;
        }
    }
}
