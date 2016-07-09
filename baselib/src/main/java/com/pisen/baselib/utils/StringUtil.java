package com.pisen.baselib.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类：封装常用String 处理操作
 * @author chenkun
 * @date 16/2/22 上午11:10
 */

public class StringUtil {

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串 若字符串为null或空字符串，返回true
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str))
        {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 去除字符串中的空格
     * @param str
     * @return
     */
    public static String removeBlank(String str)
    {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        String after = m.replaceAll("");
        return after;
    }

    /**
     * 生成随机指定长度的字符串
     * @param idlen
     * @return
     */
    public static String getIDGenerator(int idlen) {
        String value = Long.toHexString(System.currentTimeMillis());
        String value1 = UUID.randomUUID().toString();
        value1 = value1.replace("-", "");
        value += value1;
        return value.substring(0, idlen);
    }

}
