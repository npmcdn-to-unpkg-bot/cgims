package com.mimi.cgims.util;

import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.WorkmanModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class AutoNumUtilTest extends BaseJunit4Test {
    @Before
    public void before() {
        initData.init();
//        initData.initTestData();
    }
    @Test
    public void getWorkmanNum() throws Exception {
        AutoNumUtil.test = true;
        List<String> workmanNums = new ArrayList<>();
        Date today = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
        Date tomorrow = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
        Date thirdDay = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");

        for(int i=0;i<5000;i++){
            Calendar c = Calendar.getInstance();
            if(i%3==0){
                c.setTime(today);
            }else if(i%2==0){
                c.setTime(tomorrow);
            }else{
                c.setTime(thirdDay);
            }
            AutoNumUtil.setC(c);
            String workmanNum = AutoNumUtil.getWorkmanNum();
//            System.out.println(workmanNum);
            assertFalse(workmanNums.contains(workmanNum));
            assertTrue(workmanNum.length()==12);
            workmanNums.add(workmanNum);
            WorkmanModel workman = new WorkmanModel();
            workman.setWorkmanNumber(workmanNum);
            workmanDao.add(workman);
        }
        System.out.println(workmanNums.size());
    }

    @Test
    public void getOrderNum() throws Exception {
        AutoNumUtil.test = true;
        List<String> orderNums = new ArrayList<>();
        Date today = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
        Date tomorrow = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
        Date thirdDay = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");

            for(int i=0;i<5000;i++){
                Calendar c = Calendar.getInstance();
                if(i%3==0){
                    c.setTime(today);
                }else if(i%2==0){
                    c.setTime(tomorrow);
                }else{
                    c.setTime(thirdDay);
                }
                AutoNumUtil.setC(c);
                String orderNum = AutoNumUtil.getOrderNum();
                assertFalse(orderNums.contains(orderNum));
                assertTrue(orderNum.length()==12);
                orderNums.add(orderNum);
                OrderModel order = new OrderModel();
                order.setOrderNumber(orderNum);
                orderDao.add(order);
            }
        System.out.println(orderNums.size());
    }

}