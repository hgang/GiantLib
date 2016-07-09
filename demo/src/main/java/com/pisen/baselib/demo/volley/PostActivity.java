package com.pisen.baselib.demo.volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.manager.HttpCallback;
import com.android.volley.manager.RequestManager;
import com.pisen.baselib.demo.R;
import com.pisen.baselib.demo.volley.engin.AccountApiConfig;
import com.pisen.baselib.demo.volley.beans.HttpResult;
import com.pisen.baselib.demo.volley.beans.SSOBean;
import com.pisen.baselib.demo.volley.beans.SendPhoneCodeBean;

import java.util.Map;

/**
 * @author hegang
 * @version 1.0, 2016/2/22 16:29
 */
public class PostActivity extends Activity {

    private Button btnExecute,btnJsonExecute;
    private EditText edtPhone, edtUrl;
    private static final int ACTION_ID = 0x54;
    private static final int ACTION_JSON = 0x55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnExecute = (Button) findViewById(R.id.post_btn_execute);
        btnJsonExecute = (Button) findViewById(R.id.post_json_btn_execute);
        edtPhone = (EditText) findViewById(R.id.post_edt_phone);
        edtUrl = (EditText) findViewById(R.id.post_edt_url);

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendSMS();
            }
        });

        btnJsonExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doJsonSendSMS();
            }
        });
    }

    private void doJsonSendSMS() {

        String url = edtUrl.getText().toString();
        String phone = edtPhone.getText().toString();

        SSOBean<SendPhoneCodeBean> bean = new SSOBean<SendPhoneCodeBean>();
        bean.AppKey = AccountApiConfig.AppKey;
        bean.Body = new SendPhoneCodeBean();
        bean.Body.AppKey = AccountApiConfig.AppKey;
        bean.Body.SendType = 0;
        bean.Body.PhoneNumber = phone;
        bean.Body.PlatformType = 2;
        bean.Format = "json";
        bean.Method = "Pisen.Service.Share.SSO.Contract.ICustomerService.AppSendMsg";
        bean.Sign = "dsjklsdkjsldksjlkdjslkdl";//TODO wrong Sign
        RequestManager.getInstance().postJson(url,bean)
                .setActionId(ACTION_JSON)
                .addCallback(mCallback).execute();
    }


    private HttpCallback<HttpResult> mCallback = new HttpCallback<HttpResult>(HttpResult.class) {
        @Override
        public void onStart() {
            Log.i("hegang", ACTION_ID + " request started");
        }

        @Override
        protected void getHeaders(Map<String, String> headers) {
            Log.i("hegang", ACTION_ID + " headers = " + headers);
        }

        @Override
        protected boolean isSuccess(HttpResult obj) {
            return obj.IsSuccess && !obj.IsError;
        }

        @Override
        public void onSuccess(HttpResult response, int actionId) {
            Log.i("hegang", ACTION_ID + " response = " + response);
        }

        @Override
        protected String getPromptMsg(HttpResult obj) {
            return obj.ErrMsg != null ? obj.ErrMsg : obj.Message;
        }

        @Override
        public void onError(String errMsg, int actionId) {
            Toast.makeText(PostActivity.this, errMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void doSendSMS() {
        String url = edtUrl.getText().toString();
        String phone = edtPhone.getText().toString();
        RequestManager.getInstance().post(url)
                .setActionId(ACTION_ID)
                .addParams(AccountApiConfig.getPhoneCodeMap(phone, 0))
                .addCallback(mCallback).execute();
    }

    @Override
    protected void onDestroy() {
        RequestManager.getInstance().cancel(ACTION_ID);
        super.onDestroy();
    }
}
