package com.android.volley.manager;

import java.util.Map;

/**
 * 请求回调<br/>
 * 注意：混淆时T必须excluded
 *
 * @author hegang
 * @version 1.0, 2016/2/22 9:45
 */
public abstract class HttpCallback<T> {

    Class<T> clazz;

    public HttpCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * 获取http返回的headers,只有返回200时才会回调
     *
     * @param headers
     */
    protected void getHeaders(Map<String, String> headers) {

    }

    public void onStart() {

    }

    /**
     * 返回不是200或者{@link HttpCallback#isSuccess(Object)}返回false或者response为null
     *
     * @param errMsg
     * @param actionId
     */
    public void onError(String errMsg, int actionId) {

    }

    /**
     * 返回200且{@link HttpCallback#isSuccess(Object)}返回true且解析成功response不为null
     *
     * @param response 用户设置的POJP对象
     * @param actionId
     */
    public abstract void onSuccess(T response, int actionId);

    /**
     * 根据返回对象设置判断成功失败的条件
     *
     * @param obj
     * @return 默认返回true
     */
    protected boolean isSuccess(T obj) {
        return true;
    }

    /**
     * 当{@link HttpCallback#isSuccess(Object)}返回false时出错提示信息
     *
     * @param obj
     * @return
     */
    protected String getPromptMsg(T obj) {
        return null;
    }
}