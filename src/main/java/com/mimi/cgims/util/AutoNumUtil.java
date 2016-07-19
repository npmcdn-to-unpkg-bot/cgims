package com.mimi.cgims.util;

import java.util.Calendar;

public class AutoNumUtil {

    private static AutoNumber workmanAutoNumber = new AutoNumber();

    private static AutoNumber orderAutoNumber = new AutoNumber();

    public static String getWorkmanNum() {
        return workmanAutoNumber.getNext();
    }

    public static String getOrderNum() {
        return orderAutoNumber.getNext();
    }

    private static Calendar c = Calendar.getInstance();
    public static void setC(Calendar c){
        AutoNumUtil.c = c;
    }

    public static Calendar getC(){
//        return Calendar.getInstance();
        return c;
    }

}

class AutoNumber {
    private int curYear = 0;
    private int curMonth = 0;
    private int curDay = 0;
    private int count = 0;

    public AutoNumber() {
        Calendar c = Calendar.getInstance();
        int nowYear = c.get(Calendar.YEAR);
        int nowMonth = c.get(Calendar.MONTH) + 1;
        int nowDay = c.get(Calendar.DAY_OF_MONTH);
        curYear = nowYear;
        curMonth = nowMonth;
        curDay = nowDay;
        count = 0;
    }

    synchronized public String getNext() {
        Calendar c = AutoNumUtil.getC();
        int nowYear = c.get(Calendar.YEAR);
        int nowMonth = c.get(Calendar.MONTH) + 1;
        int nowDay = c.get(Calendar.DAY_OF_MONTH);
        String year = "" + nowYear;
        String month = "" + nowMonth;
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = "" + c.get(Calendar.DAY_OF_MONTH);

        String dateStr = year + month + day;
        if (curDay == nowDay && curMonth == nowMonth && curYear == nowYear) {
            count++;
        } else {
            count = 0;
            curYear = nowYear;
            curMonth = nowMonth;
            curDay = nowDay;
        }
        for (int i = 1000; i > 1; i = i / 10) {
            if (count / i > 0) {
                break;
            }
            dateStr += "0";
        }
        return dateStr + count;
    }
}