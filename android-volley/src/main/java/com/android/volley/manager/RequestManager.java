package com.android.volley.manager;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.google.gson.utils.GsonUtils;

import java.util.Map;

/**
 * 请求管理器
 * <p>可设置默认请求默认配置<br/>
 * 发起post/get...请求<br/>
 * 可取消请求<br/>
 * </p>
 *
 * @author hegang
 * @version 1.0, 2016/2/19 15:36
 */
public class RequestManager {

    private static int actionId = 0x5432;
    private SparseArrayCompat<LoadController> mLoadControllers = new SparseArrayCompat<LoadController>();

    private volatile static RequestManager mInstance = null;

    private RequestQueue mRequestQueue = null;


    private RequestManager() {

    }

    /**
     * 设置全局默认策略-超时时间<br/>
     * 注意和{@link #setDefaultRetryTimes(int)}有关，实际超时时间 = timeout+retryTimes*timeout
     * @param defaultTimeout 超时时间，单位ms,>=0
     */
    public static void setDefaultTimeout(int defaultTimeout) {
        if (defaultTimeout < 0) {
            defaultTimeout = 0;
        }
        RequestConfig.TIMEOUT_COUNT = defaultTimeout;
    }

    /**
     * 设置全局默认策略-重试次数
     *
     * @param retryTimes 重试次数 >=0
     */
    public static void setDefaultRetryTimes(int retryTimes) {
        if (retryTimes < 0) {
            retryTimes = 0;
        }
        RequestConfig.RETRY_TIMES = retryTimes;
    }

    /**
     * 设置全局默认策略-是否缓存
     *
     * @param shouldCache
     */
    public static void setDefaultShouldCache(boolean shouldCache) {
        RequestConfig.SHOULD_CACHE = shouldCache;
    }

    public void init(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * SingleTon
     *
     * @return single Instance
     */
    public static RequestManager getInstance() {
        if (null == mInstance) {
            synchronized (RequestManager.class) {
                if (null == mInstance) {
                    mInstance = new RequestManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    /**
     * Get提交
     *
     * @param url
     * @return
     */
    public RequestCreator get(String url) {
        return RequestCreator.create(Method.GET, url);
    }

    /**
     * Post提交
     *
     * @param url
     * @return
     */
    public RequestCreator post(String url) {
        return RequestCreator.create(Method.POST, url);
    }

    /**
     * PostJson提交
     *
     * @param url
     * @param obj 可以是json String，也可以是对象
     * @return
     */
    public static RequestCreator postJson(String url, Object obj) {
        return RequestCreator.createJson(Request.Method.POST, url, obj);
    }


    /**
     * 发送网络请求
     *
     * @param creator
     * @return
     */
    LoadController sendRequest(RequestCreator creator) {
        if (mRequestQueue == null) {
            throw new IllegalStateException(RequestManager.class.getCanonicalName() + " must be initialized");
        }
        int actId = creator.hasActionId() ? creator.mActionId : RequestManager.actionId++;
        Request<?> request = null;
        Object data = creator.mData;
        int method = creator.mMethod;
        String url = creator.mUrl;
        boolean shouldCache = creator.mShouldCache;
        Map headers = creator.mHeaders;
        int retryTimes = creator.mRetryTimes;
        int timeoutCount = creator.mTimeoutCount;

        RequestLoadController loadController = new RequestLoadController(creator.mCallback, actId);
        if (creator.isJsonRequest) {
            String body = null;
            if (data != null) {
                if (data instanceof String) {
                    body = (String) data;
                } else {
                    body = GsonUtils.jsonSerializer(data);
                }
            }
            request = new JsonNetworkRequest(method, url, body, loadController, loadController);
        } else {
            if (data != null && data instanceof RequestMap) {// force POST and No Cache
                request = new ByteArrayRequest(Method.POST, url, data, loadController, loadController);
                request.setShouldCache(false);
            } else {
                request = new ByteArrayRequest(method, url, data, loadController, loadController);
                request.setShouldCache(shouldCache);
            }
            if (headers != null && !headers.isEmpty()) {// add headers if not empty
                try {
                    request.getHeaders().putAll(headers);
                } catch (AuthFailureError e) {
                    e.printStackTrace();
                }
            }
        }

        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutCount,
                retryTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        loadController.bindRequest(request);
        if (this.mRequestQueue == null) {
            throw new NullPointerException();
        }
        mLoadControllers.put(actId, loadController);
        if (creator.mCallback != null) {
            creator.mCallback.onStart();
        }
        this.mRequestQueue.add(request);
        return loadController;
    }

    void removeLoadController(int actionId) {
        synchronized (mLoadControllers) {
            mLoadControllers.remove(actionId);
        }
    }

    /**
     * 取消请求
     *
     * @param actionId 请求Id
     */
    public void cancel(int actionId) {
        synchronized (mLoadControllers) {
            LoadController loadController = mLoadControllers.get(actionId);
            if (loadController != null) {
                loadController.cancel();
            }
        }
    }
}
