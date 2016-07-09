package com.android.volley.manager;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.utils.GsonUtils;

import java.io.UnsupportedEncodingException;

/**
 * 请求-响应控制器
 *
 * @author hegang
 */
public class RequestLoadController<T> extends AbsLoadController implements Response.Listener<NetworkResponse>, Response.ErrorListener {


    private HttpCallback<T> mHttpCallback;
    private int mAction = 0;

    public RequestLoadController(HttpCallback callback, int actionId) {
        this.mHttpCallback = callback;
        this.mAction = actionId;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        RequestManager.getInstance().removeLoadController(mAction);
        String errorMsg = null;
//        if (VolleyLog.DEBUG) {
            if (error.networkResponse == null) {
                error.printStackTrace();
            } else {
                VolleyLog.e("%s",error.toString());
            }
//        }
        if (error.getMessage() != null) { //异常信息
            errorMsg = error.getMessage();
            VolleyLog.e(errorMsg);
            if (errorMsg.contains("UnknownHostException")) {//未联网或者网络不通时
                errorMsg = RequestConfig.MSG_NETWORK_ERROR;
            } else {
                try {
                    errorMsg = RequestConfig.MSG_SERVER_ERROR + error.networkResponse.statusCode;
                } catch (Exception e) {
                    errorMsg = RequestConfig.MSG_SERVER_ERROR;
                }
            }
        } else {
            try {
                errorMsg = RequestConfig.MSG_SERVER_ERROR + error.networkResponse.statusCode;
            } catch (Exception e) {
                errorMsg = RequestConfig.MSG_SERVER_ERROR;
            }
        }
        this.mHttpCallback.onError(errorMsg, this.mAction);
    }

    @Override
    public void onResponse(NetworkResponse response) {
        RequestManager.getInstance().removeLoadController(mAction);
        this.mHttpCallback.getHeaders(response.headers);
        String parsed = null;
        T obj;
        try {
            parsed = new String(response.data, RequestConfig.CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        VolleyLog.d("onResponse ret = "+parsed);
        if ("java.lang.String".equals(mHttpCallback.getClazz().getCanonicalName())) {//String 则不用解析
            this.mHttpCallback.onSuccess((T) parsed, mAction);
        } else {
            obj = GsonUtils.jsonDeserializer(parsed, mHttpCallback.getClazz());
            if (obj == null) {//解析出错
                this.mHttpCallback.onError(RequestConfig.MSG_PARSE_ERROR, mAction);
            } else if (mHttpCallback.isSuccess(obj)) {
                this.mHttpCallback.onSuccess(obj, mAction);
            } else {
                String promptMsg = mHttpCallback.getPromptMsg(obj);
                if (TextUtils.isEmpty(promptMsg)) {
                    promptMsg = RequestConfig.MSG_UNKNOWN_ERROR;
                    VolleyLog.e("getPromptMsg isEmpty");
                }
                this.mHttpCallback.onError(promptMsg, mAction);
            }
        }
    }
}