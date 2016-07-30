package com.mimi.cgims.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestUtil {
    public static void makeError() {
        for (int i = 0; i < 10; i++) {
            System.out.println(1 / (2 - i));
        }
    }

    public static void main(String args[]) {
        final List<String> list = Collections.synchronizedList(new ArrayList<String>());
        final ExecutorService executorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 200; i++) {
            final int k = i;
            new Runnable() {
                public void run() {
                    if(k%20==0){
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH,19);
//                        AutoNumUtil.setC(c);
                    }
                    String num = AutoNumUtil.getOrderNum();
                    if(list.contains(num)){
                        System.out.println("error"+num);
                    }
                    list.add(num);
                    System.out.println("order:"+num+executorService.isTerminated()+":"+list.size());

//                    System.out.println("workman:"+AutoNumUtil.getWorkmanNum());
//                    System.out.println(getNext());
//                    executorService.shutdownNow()
//                    if(executorService.isTerminated()){
                    executorService.shutdown();
//                    }
                }
            };

            executorService.execute(()->{
                System.out.println("hello,lambda");
            });
        }
    }
//
//    private static int curDay = 0;
//    private static int count = 0;
//
//    synchronized public static String getNext() {
//        Calendar c = Calendar.getInstance();
//        String year = "" + c.get(Calendar.YEAR);
//        String month = "" + (c.get(Calendar.MONTH) + 1);
//        if (month.length() == 1) {
//            month = "0" + month;
//        }
//        String day = "" + c.get(Calendar.DAY_OF_MONTH);
//
//        String dateStr = year + month + day;
//        int nowDay = c.get(Calendar.DAY_OF_YEAR);
//        if (curDay == nowDay) {
//            count++;
//        } else {
//            count = 0;
//            curDay = nowDay;
//        }
//        for (int i = 1000; i > 1; i = i / 10) {
//            if (count / i > 0) {
//                break;
//            }
//            dateStr += "0";
//        }
//        return dateStr + count;
//    }
}
