package com.mimi.cgims;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CommonTest {
    @Test
    public void jsonData() throws Exception {
        Map<String, String[]> serviceItems = Constants.getServiceItemsMap();
//                Map<String, List> newItemsMap = new HashMap<>();
        Map<String, String> newItemsMap = new HashMap<>();
        for (String key : serviceItems.keySet()) {
            if (Math.random() > 0.5) {
//                        List<String> newItems = new ArrayList<>();
                String newItems = "";
                String[] items = serviceItems.get(key);
                for (String item : items) {
                    if (Math.random() > 0.5) {
//                                newItems.add(item);
                        if(StringUtils.isNotBlank(newItems)){
                            newItems+=",";
                        }
                        if(item.equals("其他")){
                            item = "阿里山的风景阿拉山口大就发生的";
                        }
                        newItems+=item;
                    }
                }
                if (!newItems.isEmpty()) {
                    newItemsMap.put(key, newItems);
                }
            }
        }
        System.out.println(JSONObject.fromObject(newItemsMap).toString());
    }
    @Test
    public void randomFloat() throws Exception{

        Random random = new Random();
        for(int i=0;i<10;i++)
        System.out.println((float)(Math.round(random.nextFloat()*5*100))/100);
    }
}
