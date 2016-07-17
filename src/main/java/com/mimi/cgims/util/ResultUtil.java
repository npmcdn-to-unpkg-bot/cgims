package com.mimi.cgims.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultUtil {
    public static final String MAP_KEY_SUCCESS = "success";

    public static final String MAP_KEY_MSG = "msg";

    public static final String MAP_KEY_RESULT = "result";

    public static final String MAP_KEY_DATAS = "datas";

    public static final String MAP_KEY_TOTAL = "total";

    public static final String MAP_KEY_TOTAL_PAGE = "totalPage";

    public static final String MAP_KEY_CUR_PAGE = "curPage";

    public static final String MAP_KEY_PAGE_SIZE = "pageSize";

    public static final String MAP_KEY_DATA = "data";

    public static Map<String, Object> getResultMap(boolean success, String msg, Object result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(MAP_KEY_SUCCESS, success);
        resultMap.put(MAP_KEY_MSG, msg);
        resultMap.put(MAP_KEY_RESULT, result);
        return resultMap;
    }

    public static Map<String, Object> getSuccessResultMap() {
        return getResultMap(true, null, null);
    }


    public static Map<String, Object> getSuccessResultMap(String msg) {
        return getResultMap(true, msg);
    }


    public static Map<String, Object> getFailResultMap(String msg) {
        return getResultMap(false, msg);
    }

    public static <E> Map<String, Object> getResultMap(boolean success, String msg, int total, int totalPage, int curPage,
                                                       int pageSize, List<E> items) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(MAP_KEY_TOTAL, total);
        map.put(MAP_KEY_CUR_PAGE, curPage);
        map.put(MAP_KEY_TOTAL_PAGE, totalPage);
        map.put(MAP_KEY_PAGE_SIZE, pageSize);
        map.put(MAP_KEY_DATAS, items);

        resultMap.put(MAP_KEY_SUCCESS, success);
        resultMap.put(MAP_KEY_MSG, msg);
        resultMap.put(MAP_KEY_RESULT, map);

        return resultMap;
    }


    public static <E> Map<String, Object> getResultMap(int total, int totalPage, int curPage,
                                                       int pageSize, List<E> items) {
        return getResultMap(true, null, total, totalPage, curPage, pageSize, items);
    }

    public static Map<String, Object> getResultMap(boolean success, String msg) {
        return getResultMap(success, msg, null);
    }

}
