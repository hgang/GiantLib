package com.heg.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

/**
 * SharedPreferences工具类
 * 需在应用开启 @{@link android.app.Application} onCreate();调用initSPUtils(Context context);
 *
 * 默认采用应用名作为内容名
 *
 *
 * @author shichuang
 * @date 2016/2/24 0024 11:15
 */
@SuppressWarnings("all")
public class SharedPreferencesUtil {

	//缓存初始化上下文对象
	private static Context mContext;

	//默认SP对象
	private static SharedPreferences mSP;

	//默认配置文件名,应用名
	private static String mAppName;

	private SharedPreferencesUtil() {}

	/**
	 * 对默认的SP对象进行初始化操作
	 *
	 * @param context
	 */
	public static void initSPUtils(Context context) {
		mContext = context;
		try {
			PackageManager pm = context.getApplicationContext().getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
			mAppName = (String)pm.getApplicationLabel(appInfo);

		}
		catch(PackageManager.NameNotFoundException e) {

			mAppName = "SharedPrefrencesUtil";
			//TODO LogCat
		}
		mSP = context.getSharedPreferences(mAppName, Context.MODE_PRIVATE);
	}

	/**
	 * 根据是否包含自定义名称来判断所要操作的SP对象
	 *
	 * @param name
	 * @return
	 */
	private static SharedPreferences checkSp(String name) {
		isSecurity();
		return null == name ? mSP : mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * 判断是否进行过初始化检测,如未进行初始化,则抛出异常
	 */
	private static void isSecurity() {
		if(null == mSP || null == mContext) {
			//TODO LogCat
			throw new RuntimeException();
		}
	}
	/**
	 * 使用默认SP对象进行String数据存储
	 *
	 * @param key
	 * @param value
	 */
	public static void putString(String key, String value) {
		putString(key, value, null);
	}

	/**
	 * 使用自定义文件名对String数据存储
	 *
	 * @param key
	 * @param value
	 * @param name 自定义配置文件名
	 */
	public static void putString(String key, String value, String name) {
		checkSp(name).edit().putString(key, value).commit();
	}

	public static String getString(String key, String defaultValue) {
		return getString(key, defaultValue, null);
	}

	public static String getString(String key, String defalutValue, String name) {
		return checkSp(name).getString(key, defalutValue);
	}

	//Boolean
	public static void putBoolean(String key, boolean value) {
		putBoolean(key, value, null);
	}

	public static void putBoolean(String key, boolean value, String name) {
		checkSp(name).edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key, boolean defalutValue) {
		return getBoolean(key, defalutValue, null);
	}

	public static boolean getBoolean(String key, boolean defalutValue, String name) {
		return checkSp(name).getBoolean(key, defalutValue);
	}

	//Float
	public static void putFloat(String key, float value) {
		putFloat(key, value, null);
	}

	public static void putFloat(String key, float value, String name) {
		checkSp(name).edit().putFloat(key, value).commit();
	}

	public static float getFloat(String key, float defalutValue) {
		return getFloat(key, defalutValue, null);
	}

	public static float getFloat(String key, float defalutValue, String name) {
		return checkSp(name).getFloat(key, defalutValue);
	}

	//Integer
	public static void putInt(String key, int value) {
		putLong(key, value, null);
	}

	public static void putInt(String key, int value, String name) {
		checkSp(name).edit().putInt(key, value).commit();
	}

	public static int getInt(String key, int defalutValue) {
		return getInt(key, defalutValue, null);
	}

	public static int getInt(String key, int defalutValue, String name) {
		return checkSp(name).getInt(key, defalutValue);
	}

	//Long
	public static void putLong(String key, long value) {
		putLong(key, value, null);
	}

	public static void putLong(String key, long value, String name) {
		checkSp(name).edit().putLong(key, value).commit();
	}

	public static long getLong(String key, long defalutValue) {
		return getLong(key, defalutValue, null);
	}

	public static long getLong(String key, long defalutValue, String name) {
		return checkSp(name).getLong(key, defalutValue);
	}

	//Set<String>
	public static void putStringSet(String key, Set<String> value) {
		putStringSet(key, value, null);
	}

	public static void putStringSet(String key, Set<String> value, String name) {
		checkSp(name).edit().putStringSet(key, value).commit();
	}

	public static Set<String> getStringSet(String key, Set<String> defalutValue) {
		return getStringSet(key, defalutValue, null);
	}

	public static Set<String> getStringSet(String key, Set<String> defalutValue, String name) {
		return checkSp(name).getStringSet(key, defalutValue);
	}

	//Clean
	public static void clearValue(String key) {
		clearValue(key, null);
	}

	/**
	 * 清除指定配置文件中的指定数据
	 *
	 * @param key
	 * @param name
	 */
	public static void clearValue(String key, String name) {
		checkSp(name).edit().remove(key).commit();
	}

	//Remove
	public static void remove() {
		remove(null);
	}

	/**
	 * 清除指定文件中的所有数据
	 *
	 * @param name
	 */
	public static void remove(String name) {
		checkSp(name).edit().clear().commit();
	}

	public static void putObject(String key, Object value) {
		putObject(key, value, null);
	}

	/**
	 *  将对象进行base64编码后保存到SharePref中
	 * @param key
	 * @param value
	 * @param name
	 */
	public static void putObject(String key, Object value, String name) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			// 将对象的转为base64码
			String objBase64 = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
			checkSp(name).edit().putString(key, objBase64).commit();
		}
		catch(IOException e) {
			//TODO LogCat
			e.printStackTrace();
		}
		finally {
			if(null != oos) {
				try {
					oos.close();
				}
				catch(IOException e) {
				}
			}
		}
	}

	public static void getObject(String key) {
		getObject(key, null);
	}

	/**
	 * 将SharePref中经过base64编码的对象读取出来
	 * @param key
	 * @param name
	 * @return
	 */
	public static Object getObject(String key, String name) {
		String objBase64 = checkSp(name).getString(key, null);
		if(TextUtils.isEmpty(objBase64))
			return null;

		// 对Base64格式的字符串进行解码
		byte[] base64Bytes = Base64.decode(objBase64.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

		ObjectInputStream ois = null;
		Object obj = null;
		try {
			ois = new ObjectInputStream(bais);
			obj = (Object)ois.readObject();
		}
		catch(Exception e) {
			//TODO LogCat
			e.printStackTrace();
		}
		finally {
			if(null != ois) {
				try {
					ois.close();
				}
				catch(IOException e) {}
			}
		}
		return obj;
	}
}
