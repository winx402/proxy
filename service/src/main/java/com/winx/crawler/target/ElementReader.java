package com.winx.crawler.target;

import com.winx.exception.ProcessException;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
public interface ElementReader<F, T extends TargetWebGetter> {
    boolean read(F f, T t) throws ProcessException;

    String getInfo();
}
