package com.mimi.cgims.util;

import com.mimi.cgims.Constants;
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
}
