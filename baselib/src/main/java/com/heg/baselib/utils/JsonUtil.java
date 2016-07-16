package com.heg.baselib.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Json解析辅助类
 *	实现对Bean对象的序列化Json类型的String , 以及解析JsonString为多类型对象
 *
 * @author shichuang
 * @date 2016/2/22 0022 11:36
 */
public class JsonUtil {

	/**
	 * JsonString 转 JavaBean
	 *
	 * @param jsonString
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T parseBean(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			//TODO LogCat
		}
		return t;
	}

	/**
	 * JsonString 转 List<JavaBean>
	 *
	 * @param jsonString
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> parseBeans(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			//TODO LogCat
		}
		return list;
	}

	/**
	 * 	JsonString 转 List<Map<String , Object>>
	 *
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> parseBeansMap(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonString,
					new TypeReference<List<Map<String, Object>>>() {
					});
		} catch (Exception e) {
			//TODO LogCat
		}
		return list;
	}

	/**
	 * JavaBean 转 JsonString
	 *
	 * @param obj
	 * @return
	 */
	public static String serialize(Object obj){
		return serialize(obj,SerializerFeature.WriteMapNullValue);
	}

	/**
	 * JavaBean 转 JsonString(带格式,方便Log格式输出查看)
	 *
	 * @param obj
	 * @param hasFormat 是否需要格式化输出
	 * @return
	 */
	public static String serialize(Object obj , boolean hasFormat){
		if(hasFormat){
			return  serialize(obj, SerializerFeature.PrettyFormat);
		} else {
			return serialize(obj);
		}

	}

	/**
	 * JavaBean 转 JsonString 过滤字段
	 *
	 * @param obj
	 * @param fields 所需要过滤的字段
	 * @return
	 */
	public static String serialize(Object obj, String... fields){
		String result = null;
		try {
			result = JSON.toJSONString(obj, new SimplePropertyPreFilter(obj.getClass(), fields),SerializerFeature.WriteMapNullValue);
		}catch(Exception e){
			//TODO LogCat
		}
		return result;
	}


	/**
	 * JavaBean 转 JsonString(配置参数) {@link com.alibaba.fastjson.serializer.SerializerFeature }
	 *
	 *
	 *	QuoteFieldNames———-输出key时是否使用双引号,默认为true
	 *	WriteMapNullValue——–是否输出值为null的字段,默认为false
	 *	WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
	 *	WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
	 *	WriteNullStringAsEmpty—字符类型字段如果为null,输出为"",而非null
	 *	WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
	 *
	 * @param obj
	 * @param features 输出格式的配置
	 * @return
	 */
	public static String serialize(Object obj, SerializerFeature... features){
		return JSON.toJSONString(obj,features);
	}
}
