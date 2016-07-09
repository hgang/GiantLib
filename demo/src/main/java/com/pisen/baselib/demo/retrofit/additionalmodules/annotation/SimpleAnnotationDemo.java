package com.pisen.baselib.demo.retrofit.additionalmodules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Created by yangbo on 2016/4/7.
 *
 * @version 1.0
 */
public class SimpleAnnotationDemo {

    public static class Test {
        @txtValue("测试键")
        public static final String TEST = "测试值";
    }
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface txtValue {
        String value() default "";
    }

    public static void main(String[] args){
        Class claz = Test.class;
        Field[] fields = claz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SimpleAnnotationDemo.txtValue.class)) {
                SimpleAnnotationDemo.txtValue anno = field.getAnnotation(SimpleAnnotationDemo.txtValue.class);
                try {
                    String annoValue = anno.value();
                    String Value = field.get(null).toString();
                    System.out.println(field.getName() + "=" + Value + "\nAnnotation:"+annoValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
