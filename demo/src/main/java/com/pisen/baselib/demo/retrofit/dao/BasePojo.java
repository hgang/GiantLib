package com.pisen.baselib.demo.retrofit.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbo on 2016/4/6.
 *
 * @version 1.0
 */
public class BasePojo {

    /**
     * 获取所有field 包括父类
     *
     * @param clz       当前class
     * @param fieldList 装在field list
     */
    public static void setAllFields2List(Class clz, List<Field> fieldList) {
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            fieldList.add(field);
        }
        if (clz.getSuperclass() != null) {
            setAllFields2List(clz.getSuperclass(), fieldList);
        }
    }

    /**
     * 将所有变量通过反射转变map，并不通用（类变量无法转变）
     *
     * @return
     */
    public Map<String, Object> fielddtoMap() {
        List<Field> fieldList = new ArrayList<>();
        setAllFields2List(this.getClass(), fieldList);
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        Map<String, Object> parmsMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();
            try {
                Object objValue = field.get(this);
                parmsMap.put(key, objValue == null ? "" : objValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return parmsMap;
    }

    @Override
    public String toString() {

        List<Field> fieldList = new ArrayList<>();
        setAllFields2List(this.getClass(), fieldList);
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        StringBuilder strBd = new StringBuilder();
        for (Field field : fields) {
            String key = field.getName();
            try {
                Object objValue = field.get(this);
                strBd.append(key)
                        .append(" = ")
                        .append(objValue == null ? "" : objValue.toString())
                        .append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return strBd.toString();
    }
}
