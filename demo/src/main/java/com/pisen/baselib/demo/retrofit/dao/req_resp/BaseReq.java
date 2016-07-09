package com.pisen.baselib.demo.retrofit.dao.req_resp;

import com.pisen.baselib.demo.retrofit.dao.BasePojo;
import com.pisen.baselib.demo.volley.engin.HmacEncryptUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yangbo on 2016/4/6.
 *
 * @version 1.0
 */
public class BaseReq extends BasePojo {

    private final String AppSecret = "206c86ce9f304800b7cdc19c03a2cddd";

    final String AppKey = "15121510";
    String Body;
    String Format;
    String Method;
    String SessionKey;
    String Version;

    String Sign;

    /**
     * 生成并设置签名
     */
    public void generateAndSetSign() {
        List<Field> fieldList = new ArrayList<>();
        setAllFields2List(this.getClass(), fieldList);
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        new TreeMap<>();
        TreeMap<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        for (Field field : fields) {
            String key = field.getName();
            if (key.equals("AppSecret")) {
                continue;
            }
            try {
                Object objValue = field.get(this);
                map.put(key, objValue == null ? "" : objValue.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Sign = HmacEncryptUtils.encryptByHmacSha1(map, AppSecret);
    }

    public String getAppKey() {
        return AppKey;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getSign() {
        return Sign;
    }
}
