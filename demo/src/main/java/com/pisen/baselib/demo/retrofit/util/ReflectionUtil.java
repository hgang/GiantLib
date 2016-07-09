package com.pisen.baselib.demo.retrofit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by "zeng_yong_chang@163.com".
 */
public class ReflectionUtil {

    public static Object invokeMethod(Object receiver, String methodName, Class<?>[] paramTypes, Object... paramValues) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethodInternal(receiver, receiver.getClass(), methodName, paramTypes, paramValues);
    }

    public static Object invokeStaticMethod(String className, String methodName, Class<?>[] paramTypes, Object... paramValues) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethodInternal(null, Class.forName(className), methodName, paramTypes, paramValues);
    }

    private static Object invokeMethodInternal(Object receiver, Class<?> clazz, String methodName, Class<?>[] paramTypes, Object... paramValues) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(receiver, paramValues);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public static Object newInstance(String className, Class[] paramTypes, Object[] paramValues) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = Class.forName(className);
        Constructor constructor = clazz.getDeclaredConstructor(paramTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(paramValues);
    }

    // invoke no-argument-constructor
    public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(className);
        return clazz.newInstance();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public static void setValue(Object instance, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getAccessibleField(instance.getClass(), fieldName);
        field.set(instance, value);
    }

    public static Object getValue(Object instance, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getAccessibleField(instance.getClass(), fieldName);
        return field.get(instance);
    }

    public static Object getStaticFieldValue(Class clazz, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getAccessibleField(clazz, fieldName);
        return field.get(null);
    }

    private static Field getAccessibleField(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return all fields' name and associated value
     */
    public static Map<String, Object> getAllFieldNameAndValue(Object instance) {
        Map<String, Object> map = new LinkedHashMap<>(64);
        for (Field field : getAllFields(instance.getClass())) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return map;
    }
    /**
     * @return all fields' name and associated value
     */
    public static Map<String, String> getAllFieldNameAndValueStrs(Object instance) {
        Map<String, String> map = new LinkedHashMap<>(64);
        for (Field field : getAllFields(instance.getClass())) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(instance)==null?"":field.get(instance).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return map;
    }

    /**
     * Return a list of all fields (whatever access status, and on whatever
     * superclass they were defined) that can be found on this class.
     * This is like a union of {@link Class#getDeclaredFields()} which
     * ignores and super-classes, and {@link Class#getFields()} which ignored
     * non-public fields
     *
     * @param clazz The class to introspect
     * @return The complete list of fields
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(clazz);
        classes.addAll(getAllSuperclasses(clazz));
        return getAllFields(classes);
    }

    /**
     * As {@link #getAllFields(Class)} but acts on a list of {@link Class}s and
     * uses only {@link Class#getDeclaredFields()}.
     *
     * @param classes The list of classes to reflect on
     * @return The complete list of fields
     */
    private static List<Field> getAllFields(List<Class<?>> classes) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz : classes) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Return a List of super-classes for the given class.
     *
     * @param clazz the class to look up
     * @return the List of super-classes in order going up from this one
     */
    public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
}
