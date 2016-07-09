package com.android.volley.manager;

import com.android.volley.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 请求创建器，支持链式编程
 *
 * @author hegang
 * @version 1.0, 2016/2/22 15:36
 */
public class RequestCreator {

    int mMethod;
    String mUrl;
    Map<String, String> mHeaders;
    Object mData;
    HttpCallback mCallback;
    boolean mShouldCache;
    int mTimeoutCount;
    int mRetryTimes;
    int mActionId = -1;
    boolean isJsonRequest = false;


    RequestCreator(int method, String url) {
        mUrl = url;
        mMethod = method;
        mTimeoutCount = RequestConfig.TIMEOUT_COUNT;
        mRetryTimes = RequestConfig.RETRY_TIMES;
        mShouldCache = RequestConfig.SHOULD_CACHE;
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public static RequestCreator create(int method, String url) {
        return new RequestCreator(method, url);
    }

    public static RequestCreator createJson(int method, String url, Object obj) {
        RequestCreator creator = new RequestCreator(method, url);
        creator.isJsonRequest = true;
        creator.mData = obj;
        return creator;
    }

    /**
     * 增加请求头
     *
     * @param headers
     * @return
     */
    public RequestCreator addHeaders(Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    /**
     * 增加参数
     *
     * @param data Get请求必须是Map类型，并且会将参数append到url上
     * @return
     */
    public RequestCreator addParams(Object data) {
        if (isJsonRequest && mData != null) {
            throw new IllegalArgumentException("json request already has params");
        }
        if (mMethod == Request.Method.GET) {
            if (data instanceof Map<?, ?>) {
                Map<String, String> map = (Map<String, String>) data;
                mUrl = mUrl + "?" + encodeParameters(map, RequestConfig.CHARSET_UTF_8);
                return this;
            } else {
                throw new IllegalArgumentException("Get request params must be instance of Map");
            }
        }
        mData = data;
        return this;
    }

    /**
     * 设置actionId
     *
     * @param actionId
     * @return
     */
    public RequestCreator setActionId(int actionId) {
        if (actionId < 0) {
            throw new IllegalArgumentException("actionId must be greater than zero!");
        }
        mActionId = actionId;
        return this;
    }

    /**
     * 增加回调
     *
     * @param callback
     * @return
     */
    public <T> RequestCreator addCallback(HttpCallback<T> callback) {
        mCallback = callback;
        return this;
    }

    /**
     * 设置超时时间<br/>
     * 注意和{@link #setRetryTimes(int)}有关，实际超时时间 = timeout+retryTimes*timeout
     *
     * @param timeout 超时时间，单位ms,>=0
     */
    public RequestCreator setTimeoutCount(int timeout) {
        mTimeoutCount = timeout;
        return this;
    }

    /**
     * 设置重试次数
     *
     * @param retryTimes
     * @return
     */
    public RequestCreator setRetryTimes(int retryTimes) {
        mRetryTimes = retryTimes;
        return this;
    }

    /**
     * 设置是否缓存
     *
     * @param shouldCache
     * @return
     */
    public RequestCreator setShouldCache(boolean shouldCache) {
        mShouldCache = shouldCache;
        return this;
    }

    /**
     * 判断是否设置了actionId
     *
     * @return
     */
    public boolean hasActionId() {
        return mActionId > 0;
    }

    /**
     * 发起请求
     *
     * @return
     */
    public LoadController execute() {
        return RequestManager.getInstance().sendRequest(this);
    }
}