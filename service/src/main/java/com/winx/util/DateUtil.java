package com.winx.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @author wangwenxiang
 * @create 2017-04-08.
 */
public class DateUtil {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

    private static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final long HOUR = 1000*60*60;

    /**
     * 给定时间与现在相差几个小时
     */
    public static int diffHours(String time){
        if (time.length() > 19){
            time = time.substring(0,19);
        }
        DateTime dateTime = stringToDateTime(time, getFromat(time));
        DateTime now = new DateTime();
        long millis = now.getMillis() - dateTime.getMillis();
        return (int) (millis / HOUR);
    }

    /**
     * String 转 DateTime
     */
    public static DateTime stringToDateTime(String time,String format){
        return DateTime.parse(time, DateTimeFormat.forPattern(format));
    }

    /**
     * 根据时间String长度 获取时间格式
     */
    private static String getFromat(String time){
        if (time.length() == 10) return YYYY_MM_DD;
        if (time.length() == 13) return YYYY_MM_DD_HH;
        if (time.length() == 16) return YYYY_MM_DD_HH_MM;
        return YYYY_MM_DD_HH_MM_SS;
    }
}
