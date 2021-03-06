package com.mimi.cgims.util;

import com.mimi.cgims.Constants;
import com.mimi.cgims.model.BaseModel;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static List<String> getListByStr(String str) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            for (String s : str.split(Constants.SPLIT_STRING_PARAMS)) {
                if (StringUtils.isNotBlank(s)) {
                    list.add(s.trim());
                }
            }
        }
        return list;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static String buildStr(List<String> list) {
        if (isEmpty(list)) {
            return "";
        }
        String str = "";
        for (String s : list) {
            if (StringUtils.isNotBlank(s)) {
                if (StringUtils.isNotBlank(str)) {
                    str += Constants.SPLIT_STRING_PARAMS;
                }
                str += s.trim();
            }
        }
        return str;
    }

    public static <M extends BaseModel> String buildIds(List<M> list) {
        if (isEmpty(list)) {
            return "";
        }
        String str = "";
        for (M model : list) {
            if (StringUtils.isNotBlank(str)) {
                str += Constants.SPLIT_STRING_PARAMS;
            }
            str += model.getId();
        }
        return str;
    }

    public static boolean contains(String[] strs, String str) {
        if (strs != null && str != null) {
            for (String s : strs) {
                if (s.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean contains(int[] strs, int str) {
        for (int s : strs) {
            if (s == str) {
                return true;
            }
        }
        return false;
    }


    public static Object[] concat(Object value, Object[] values) {
        Object[] arr = new Object[values.length + 1];
        arr[0] = value;
        for (int i = 0; i < values.length; i++) {
            arr[i + 1] = values[i];
        }
        return arr;
    }
}
