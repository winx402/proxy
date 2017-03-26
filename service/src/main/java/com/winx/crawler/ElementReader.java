package com.winx.crawler;

import org.dom4j.Element;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
public interface ElementReader<F,T extends TargetWebGetter> {
    boolean read(F f,T t);

    String getInfo();
}
