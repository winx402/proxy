package com.winx.crawler.target.attrable;

import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public abstract class AbstractAttributeParser<T> {
    private final Map<String, AttributeProcesser<T>> processerMap;

    protected AbstractAttributeParser(Map<String, AttributeProcesser<T>> processerMap) {
        this.processerMap = processerMap;
    }

    public AttributeProcesser<T> getProcesserFromKey(String key) {
        Preconditions.checkNotNull(processerMap, "processerMap is null");
        Preconditions.checkNotNull(key, "getProcesserFromKey key is null");
        return processerMap.get(key);
    }
}
