package com.mimi.cgims.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String DEFAULT_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    public static String toString(Date date, String formatStr) {
        if (StringUtils.isNotBlank(formatStr)) {
            DateFormat format = new SimpleDateFormat(formatStr);
            return format.format(date);
        }
        return null;
    }

    public static String toString(Date date) {
        return toString(date, DEFAULT_FORMAT_STR);
    }


    public static Date toDate(String str, String formatStr) {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 2012-02-24
//        date = java.sql.Date.valueOf(str);

        return date;
    }


    public static Date toDate(String str) {
        return toDate(str, DEFAULT_FORMAT_STR);
    }

    public static Date randomDate(String beginDate, String endDate) {

        try {

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//            Date start = format.parse(beginDate);//构造开始日期
//
//            Date end = format.parse(endDate);//构造结束日期

            Date start = toDate(beginDate);

            Date end = toDate(endDate);

//getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

            if (start.getTime() >= end.getTime()) {

                return null;

            }

            long date = random(start.getTime(), end.getTime());

            return new Date(date);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    private static long random(long begin, long end) {

        long rtn = begin + (long) (Math.random() * (end - begin));

//如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

        if (rtn == begin || rtn == end) {

            return random(begin, end);

        }

        return rtn;

    }

    public static void main(String args[]) {
        String str = "2012-02-22";
        Date date = toDate(str);
        String str2 = toString(date);
        System.out.println(date.getTime());
        System.out.println(str2);
    }

    public static String convert2String(int year, int month, int day) {
        String monthStr = "" + month;
        String dayStr = "" + day;
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        return year + monthStr + dayStr;
    }
}
