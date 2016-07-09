package com.pisen.baselib;

import android.test.AndroidTestCase;

import com.pisen.baselib.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 有关SPUtils的单元测试
 * @author shichuang
 * @date 2016/2/24 0024 15:15
 */
public class SharedPreferencesTest extends AndroidTestCase {

	public static final String DEFAULT_STRING = "default";

	public static final boolean DEFAULT_BOOLEAN = false;

	public static final String PREFERENCE_NAME = "PREFERENCE_NAME";
	public void testPutString() {
		String result = "bcd";
		try {
			SharedPreferencesUtil.initSPUtils(this.getContext());
			SharedPreferencesUtil.putString("abc", "bcd", PREFERENCE_NAME);
			String data = SharedPreferencesUtil.getString("abc", DEFAULT_STRING, PREFERENCE_NAME);
			assertEquals(result, data);
		}catch(Exception e){
			throw e;
		}
	}

	public void testPutBoolean() {
		boolean result = true;
		try {
			SharedPreferencesUtil.initSPUtils(this.getContext());
			SharedPreferencesUtil.putBoolean("abc", true, PREFERENCE_NAME);
			boolean data = SharedPreferencesUtil.getBoolean("abc", false, PREFERENCE_NAME);
			assertEquals(result, data);
		}catch(Exception e){
			throw e;
		}
	}

	public void testPutObject() {
		List<String> result = new ArrayList<>();
		result.add("abc");
		result.add("def");
		try {
			SharedPreferencesUtil.initSPUtils(this.getContext());
			SharedPreferencesUtil.putObject("abc", result,PREFERENCE_NAME);
			List<String> data = (List<String>) SharedPreferencesUtil.getObject("abc", PREFERENCE_NAME);
			assertEquals(result.get(0), data.get(0));
		}catch(Exception e){
			throw e;
		}
	}


	public void testClear() {
		String result = "default";
		try {
			SharedPreferencesUtil.initSPUtils(this.getContext());
			SharedPreferencesUtil.clearValue("abc",PREFERENCE_NAME);
			String data = SharedPreferencesUtil.getString("abc", "default", PREFERENCE_NAME);
			assertEquals(result, data);
		}catch(Exception e){
			throw e;
		}
	}

}
