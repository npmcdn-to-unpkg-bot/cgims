package com.mimi.cgims.enums;

import java.util.Random;

public enum OrderStatus {
    WSWF("为收未付",0),WSXF("未收需付",1),YSWF("已收未付",2),YSXF("已收需付",3),WSWW("未收未完",4),WSWC("未收完成",5),YSWW("已收未完",6),YSWC("已收完成",7),WSSB("未收失败",8),YSSB("已收失败",9);

    private String name;
    private int index;
    // 构造方法
    private OrderStatus(String name,int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static OrderStatus[] listAll(){
       return OrderStatus.values();
    }
    public static OrderStatus[] listPersonView(){
        return new OrderStatus[]{WSWF, YSWF};
    }
    public static OrderStatus[] listPersonOperation(){
        return new OrderStatus[]{WSWF, WSXF, YSWF, YSXF, WSSB, YSSB};
    }

    public static OrderStatus random(){
        Random random= new Random();
        OrderStatus[] orderStatuses = listAll();
        int  num=random.nextInt(orderStatuses.length);
        return orderStatuses[num];
    }

}
