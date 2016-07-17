package com.mimi.cgims.util;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestUtil {
    public static void makeError() {
        for (int i = 0; i < 10; i++) {
            System.out.println(1 / (2 - i));
        }
    }

    public static void main(String args[]) {
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 100; i++) {
            final int k = i;
            executorService.execute((new Runnable() {
                public void run() {
                    System.out.println(getNext());
                }
            }));
        }
    }

    private static int curDay = 0;
    private static int count = 0;

    synchronized public static String getNext() {
        Calendar c = Calendar.getInstance();
        String year = "" + c.get(Calendar.YEAR);
        String month = "" + (c.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = "" + c.get(Calendar.DAY_OF_MONTH);

        String dateStr = year + month + day;
        int nowDay = c.get(Calendar.DAY_OF_YEAR);
        if (curDay == nowDay) {
            count++;
        } else {
            count = 0;
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
