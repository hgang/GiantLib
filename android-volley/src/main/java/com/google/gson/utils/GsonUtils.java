package com.google.gson.utils;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;


public class GsonUtils {
    static final String TAG = GsonUtils.class.getSimpleName();

    public static String jsonSerializer(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T jsonDeserializer(String json, Class<T> clazz) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(json, clazz);
        } catch (Exception e) {
            VolleyLog.e("JsonDeserializer: " + e);
        }
        return result;
    }

    public static <T> T jsonDeserializer(Reader json, Class<T> clazz) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(json, clazz);
        } catch (Exception e) {
            VolleyLog.e("JsonDeserializer: " + e);
        }
        return result;
    }

    public static <T> T jsonDeserializerCollection(String json, TypeToken<T> typeToken) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            VolleyLog.e("jsonDeserializerCollection: " + e);
        }
        return result;
    }

    public static <T> T jsonDeserializerCollection(Reader json, TypeToken<T> typeToken) {
        T result = null;
        try {
            Gson gson = new Gson();
            result = gson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            VolleyLog.e("jsonDeserializerCollection: " + e);
        }
        return result;
    }
}
