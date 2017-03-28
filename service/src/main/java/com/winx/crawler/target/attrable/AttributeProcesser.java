package com.winx.crawler.target.attrable;

import com.winx.exception.ProcessException;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public interface AttributeProcesser<T> {
    T getAttrable(String source) throws ProcessException;
}
