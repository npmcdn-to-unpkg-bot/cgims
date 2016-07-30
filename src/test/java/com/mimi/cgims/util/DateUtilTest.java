package com.mimi.cgims.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void toDate() throws Exception {

    }

    @Test
    public void toDate1() throws Exception {

    }

    @Test
    public void randomDate() throws Exception {
        String beginStr = "2013-01-01 00:00:00";
        String endStr = "2017-01-01 00:00:00";
        Calendar begin = Calendar.getInstance();
        begin.setTime(DateUtil.toDate(beginStr));
        Calendar end = Calendar.getInstance();
        end.setTime(DateUtil.toDate(endStr));
        for(int i=0;i<10;i++){
            Date date = DateUtil.randomDate(beginStr,endStr);
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(date);
            assertTrue(calendar.after(begin));
            assertTrue(calendar.before(end));
        }
    }

}