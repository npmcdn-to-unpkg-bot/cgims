package com.mimi.cgims.util;

import com.mimi.cgims.service.IAutoNumService;
import com.mimi.cgims.service.IOrderService;
import com.mimi.cgims.service.IWorkmanService;

import java.util.Calendar;

public class AutoNumUtil {

    public static boolean test = false;

    private static AutoNumber workmanAutoNumber;

    private static AutoNumber orderAutoNumber;

    public static String getWorkmanNum() {
        return workmanAutoNumber.getNext();
    }

    public static String getOrderNum() {
        return orderAutoNumber.getNext();
    }

    private static Calendar c = Calendar.getInstance();

    public static void setC(Calendar c) {
        AutoNumUtil.c = c;
    }

    public static Calendar getC() {
        if(!test){
            c = Calendar.getInstance();
        }
        return c;
    }

    public static void init(IOrderService orderService, IWorkmanService workmanService) {
        orderAutoNumber = new AutoNumber(orderService);
        workmanAutoNumber = new AutoNumber(workmanService);
    }

}

class AutoNumber {
    private int curYear = 0;
    private int curMonth = 0;
    private int curDay = 0;
    private int count = 0;
    private final int size = 4;
    private IAutoNumService autoNumService;


    public AutoNumber(IAutoNumService autoNumService) {
        this.autoNumService = autoNumService;
    }

    public void initData(int year, int month, int day) {
        count = 0;
        curYear = year;
        curMonth = month;
        curDay = day;
        if (autoNumService != null) {
            count = autoNumService.getNewestCount(year, month, day);
        }
    }

    private boolean needInit(int year, int month, int day) {
        return curDay != day || curMonth != month || curYear == year;
    }

    private void refreshCount(int year, int month, int day) {
        if (needInit(year, month, day)) {
            initData(year, month, day);
        }
        count++;
    }

    private String buildNum(int year, int month, int day) {
        String dateStr  = DateUtil.convert2String(year,month,day);
        String countStr = "" + count;
        while (countStr.length() < size) {
            countStr = "0" + countStr;
        }
        return dateStr + countStr;
    }

    synchronized public String getNext() {
        Calendar c = AutoNumUtil.getC();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        refreshCount(year, month, day);
        return buildNum(year, month, day);
    }
}