package com.mimi.cgims.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String DEFAULT_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static String toString(Date date, String formatStr) {
        if(StringUtils.isNotBlank(formatStr)){
                DateFormat format = new SimpleDateFormat(formatStr);
                return format.format(date);
        }
        return null;
    }
    public static String toString(Date date) {
        return toString(date,DEFAULT_FORMAT_STR);
    }



    public static Date toDate(String str,String formatStr) {
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
        return toDate(str,DEFAULT_FORMAT_STR);
    }

    public static void main(String args[]){
        String str = "2012-02-22";
        Date date = toDate(str);
        String str2 = toString(date);
        System.out.println(date.getTime());
        System.out.println(str2);
    }
}
