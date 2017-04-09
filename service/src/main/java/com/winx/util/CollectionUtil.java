package com.winx.util;

import java.util.Collection;
import java.util.List;

/**
 * @author wangwenxiang
 * @create 2017-04-09.
 */
public class CollectionUtil {

    public static <T> boolean containOne(Collection<T> list, Collection<T> list1){
        for (T t : list1){
            if (list.contains(t)){
                return true;
            }
        }
        return false;
    }
}
