package com.mimi.cgims.util;

import org.apache.commons.lang.StringUtils;

public class FormatUtil {
    public static final String FORMAT_ERROR_NOT_NULL = "不能为空";
    public static final String FORMAT_ERROR_COMMON = "格式有误";
    public static final String FORMAT_ERROR_LENGTH = "长度超出[{minLen},{maxLen}]的长度限制";

    public static final String REGEX_LOGIN_NAME = "^([a-zA-Z0-9_]){3,31}$";
    public static final String REGEX_USER_NAME = "^([0-9a-zA-Z_\u4E00-\u9FA5]){1,31}$";
    public static final String REGEX_USER_PHONE_NUM = "^1[0-9]{10}$";
    public static final String REGEX_USER_EMAIL = "^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9])+$";

    public static final String REGEX_NO_NEED = "";
    public static final String REGEX_COMMON_NAME = "^[0-9a-zA-Z_\u4E00-\u9FA5]+$";

    public static final String REGEX_ORDER_NUMBER = "^[0-9]{12}$";

    //热线
    public static final String REGEX_COMMON_TEL = "^((0\\d{2,3}-\\d{7,8})|(1\\d{10}))$";
    //手机号码
    public static final String REGEX_COMMON_PHONENUM = "^1[0-9]{10}$";
    //身份证号码
    public static final String REGEX_COMMON_IDENTITY = "^4[0-9]{14,17}$";
    //住户数
    public static final String REGEX_COMMON_HOUSEHOLD_NUM = "^\\d{1,6}$";
    //容积率
    public static final String REGEX_COMMON_FLOORAREARATIO = "^\\d{1,2}(\\.\\d{1,4})?$";
    //绿化率
    public static final String REGEX_COMMON_GREENRATE = "^\\d{1,2}(\\.\\d{1,4})?$";
    //停车位
    public static final String REGEX_COMMON_PARKINGSPACENUM = "^\\d{1,6}$";
    //产权年限
    public static final String REGEX_COMMON_PROPERTYYEARS = "^\\d{1,2}$";
    //物业费
    public static final String REGEX_COMMON_PROPERTYFEE = "^\\d{1,4}(\\.\\d{1,2})?$";
    //优先级
    public static final String REGEX_COMMON_PRIORITY = "^\\d{1,4}$";
    //楼盘均价
    public static final String REGEX_COMMON_AVERAGEPRICE = "^\\d{1,6}$";
    //面积
    public static final String REGEX_COMMON_GROSSFLOORAREA = "^\\d{1,6}(\\.\\d{1,2})?$";
    //租金
    public static final String REGEX_COMMON_RENTAL = "^\\d{1,6}$";
    //居住室数量
    public static final String REGEX_COMMON_ROOMNUM = "^\\d{1,2}$";
    //厅数量
    public static final String REGEX_COMMON_HALLNUM = "^\\d{1,2}$";
    //卫生间数量
    public static final String REGEX_COMMON_TOILETNUM = "^\\d{1,2}$";
    //厨房数量
    public static final String REGEX_COMMON_KITCHEN_NUM = "^\\d{1,2}$";
    //所在楼层
    public static final String REGEX_COMMON_CURFLOOR = "^\\d{1,3}$";
    //总楼层
    public static final String REGEX_COMMON_TOTALFLOOR = "^\\d{1,3}$";
    //总价格
    public static final String REGEX_COMMON_TOTALPRICE = "^\\d{1,4}$";
    //评估参数值
    public static final String REGEX_COMMON_PGPARAMETERVALUE = "^-?\\d{1,8}$";

    public static final int MIN_LENGTH_COMMON = 0;
    public static final int MIN_LENGTH_USER_NAME = 4;
    public static final int MIN_LENGTH_USER_PASSWORD = 6;

    public static final int MAX_LENGTH_COMMON = Integer.MAX_VALUE;
    public static final int MAX_LENGTH_COMMON_SHORT_L1 = 20;
    public static final int MAX_LENGTH_COMMON_SHORT_L2 = 32;
    public static final int MAX_LENGTH_COMMON_SHORT_L3 = 50;
    public static final int MAX_LENGTH_COMMON_NORMAL_L1 = 100;
    public static final int MAX_LENGTH_COMMON_NORMAL_L2 = 200;
    public static final int MAX_LENGTH_COMMON_NORMAL_L3 = 500;
    public static final int MAX_LENGTH_COMMON_LONG_L1 = 1000;
    public static final int MAX_LENGTH_COMMON_LONG_L2 = 2000;
    public static final int MAX_LENGTH_COMMON_LONG_L6 = 6000;

    public static String checkLengthOnly(String value, int maxLen,
                                         String errPrefix) {
        return checkLengthOnly(value,0,maxLen,errPrefix);
    }

    public static String checkLengthOnly(String value, int minLen, int maxLen,
                                         String errPrefix) {
        return checkFormat(value, REGEX_NO_NEED, false, minLen, maxLen,
                errPrefix);
    }

    public static String checkFormat(String value, String regex,
                                     boolean notNull, int minLen, int maxLen, String errPrefix) {
        if (StringUtils.isBlank(errPrefix)) {
            errPrefix = "";
        }
        if (StringUtils.isBlank(value)) {
            if (notNull) {
                return errPrefix + FormatUtil.FORMAT_ERROR_NOT_NULL;
            } else {
                return null;
            }
        }
        int length = value.length();
        if (length < minLen || length > maxLen) {
            String errStr = errPrefix + FORMAT_ERROR_LENGTH;
            errStr = errStr.replace("{minLen}", String.valueOf(minLen));
            errStr = errStr.replace("{maxLen}", String.valueOf(maxLen));
            return errStr;
        }
        if (checkValueFormat(value, regex)) {
            return null;
        }
        return errPrefix + FormatUtil.FORMAT_ERROR_COMMON;
    }

    public static boolean checkValueFormat(String value, String regex) {
        return checkValueFormat(value, regex, false);
    }

    public static boolean checkValueFormat(String value, String regex,
                                           boolean notNull, int maxLen) {
        return checkValueFormat(value, regex, notNull, MIN_LENGTH_COMMON,
                maxLen);
    }

    public static boolean checkValueFormat(String value, String regex,
                                           boolean notNull) {
        return checkValueFormat(value, regex, notNull, MIN_LENGTH_COMMON,
                MAX_LENGTH_COMMON);
    }

    public static boolean checkValueFormat(String value, String regex,
                                           boolean notNull, int minLen, int maxLen) {
        if (StringUtils.isBlank(value)) {
            if (notNull) {
                return false;
            } else {
                return true;
            }
        }
        int length = value.length();
        if (length < minLen || length > maxLen) {
            return false;
        }
        if (REGEX_NO_NEED.equals(regex)) {
            return true;
        }
        return value.matches(regex);
    }

    public static String checkFormat(Object obj, String regex, boolean notNull,
                                     int minLen, int maxLen, String errPrefix) {
        String value = "";
        String res = "";

        if (obj == null) {
            res = checkFormat(value, regex, notNull, minLen, maxLen, errPrefix);
        } else {
            if (obj instanceof Integer || obj instanceof String
                    || obj instanceof Double || obj instanceof Float
                    || obj instanceof Long) {
                value = String.valueOf(obj);
                res = checkFormat(value, regex, notNull, minLen, maxLen, errPrefix);

            } else {
                res = null;
            }
        }

        return res;

    }

    public static String checkFormat(Object obj, String regex, boolean notNull, String errPrefix) {
        return checkFormat(obj, regex, notNull, FormatUtil.MIN_LENGTH_COMMON, FormatUtil.MAX_LENGTH_COMMON, errPrefix);
    }

    public static String checkFormate(Object obj, boolean notNull, int maxLen, String errPrefix) {
        return checkFormat(obj, FormatUtil.REGEX_NO_NEED, notNull, FormatUtil.MIN_LENGTH_COMMON, maxLen, errPrefix);
    }

    public static void main(String[] args) {
        Object description = new Integer(190);

        System.out.println(FormatUtil.checkFormate(description, false, 2, "角色"));

        String regex = "^\\d{1,2}(\\.\\d{1,2})?$";
        Float f = new Float(23);
        System.out.println(String.valueOf(f));
        System.out.println(FormatUtil.checkFormat(f, regex, false, 0, 10, "浮点型"));
    }
}
