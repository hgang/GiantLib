package com.pisen.baselib.demo.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pisen.baselib.demo.R;
import com.pisen.baselib.demo.retrofit.dao.model.AccountOffset;
import com.pisen.baselib.demo.retrofit.dao.req_resp.AccountOffsetReq;
import com.pisen.baselib.demo.retrofit.service.IfsDefService;
import com.pisen.baselib.demo.retrofit.util.ReflectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yangbo on 2016/3/31.
 *
 * @version 1.0
 */
public class TestRotrofitAct extends BaseActivity {
    /**
     * http 地址
     */
    final String NET_ADDRESS = "http://test.api.piseneasy.com:9212/Router/Rest/";


    TextView txtReqResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act_retrofit);
        txtReqResult = (TextView) findViewById(R.id.txt_result);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    /**
     * 布局文件中定义
     *
     * @param v view
     */
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                IfsDefService service = instantiateRetrofit().create(IfsDefService.class);
                Call<String> call = service.getUser(ReflectionUtil.getAllFieldNameAndValueStrs(genReqPojo()));
            /*    try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
            case R.id.btn_post:
                postBody();
                break;
            case R.id.btn_postandretnstrs:
                postMap();
                break;
            default:
                break;
        }
    }

    /**
     * post reqPojo
     */
    private void postBody() {
        showProgressDialog();
        Retrofit retrofit = instantiateRetrofit();
        IfsDefService service = retrofit.create(IfsDefService.class);
        Call<AccountOffset> call = service.queryByPojo_POST(genReqPojo());
        call.enqueue(new Callback<AccountOffset>() {
            @Override
            public void onResponse(Call<AccountOffset> call, retrofit2.Response<AccountOffset> response) {
                dimissProgressDialog();
                String info;
                if (response.isSuccessful()) {
                    info = response.body().toString();
                } else {
                    info = "error:\n" + response.message();
                }
                txtReqResult.setText(info);
            }

            @Override
            public void onFailure(Call<AccountOffset> call, Throwable t) {
                dimissProgressDialog();
                txtReqResult.setText("Throwable:\n" + t.getMessage());
            }
        });
    }

    /**
     * post reqMap
     */
    private void postMap() {
        showProgressDialog();
        Retrofit retrofit = instantiateRetrofit();
        IfsDefService service = retrofit.create(IfsDefService.class);
        Map<String, Object> params = new HashMap<>();
        params.putAll(genReqPojo().fielddtoMap());
//        params.put("Avator",new File("/mnt/sdcard/service.db"));
        Call<AccountOffset> call = service.queryByMap_POST(params);
        call.enqueue(new Callback<AccountOffset>() {
            @Override
            public void onResponse(Call<AccountOffset> call, retrofit2.Response<AccountOffset> response) {
                dimissProgressDialog();
                String info;
                if (response.isSuccessful()) {
                    info = response.body().toString();
                } else {
                    info = "error:\n" + response.message();
                }
                txtReqResult.setText(info);
            }

            @Override
            public void onFailure(Call<AccountOffset> call, Throwable t) {
                dimissProgressDialog();
                txtReqResult.setText("Throwable:\n" + t.getMessage());
            }
        });
    }

/*    */

    /**
     * 实例化 Retrofit 对象
     */
    public Retrofit instantiateRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NET_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericHeaderClient())
                .build();
        return retrofit;
    }

    /**
     * 创建 带header的client
     */
    public OkHttpClient genericHeaderClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("version", "1")
                                .addHeader("User-Agent", "Retrofit-Sample-App")
                                .build();
                        return chain.proceed(request);
                    }
                });

        OkHttpClient client = builder.build();
        client.socketFactory();
        return client;
    }
   /* RequestBody requestBody = new RequestBody() {
        @Override
        public MediaType contentType() {
            return null;
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            contentLength();
            writeTo(Okio.buffer(new CountingSink(sink)));
        }
    };

    class CountingSink extends ForwardingSink {
        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
        }
    }*/


    /**
     * 创建 网络请求对象
     *
     * @return req对象
     */
    public AccountOffsetReq genReqPojo() {
        AccountOffsetReq reqPojo = new AccountOffsetReq();
        String phone = "13548054270";
        JSONObject body = new JSONObject();
        try {
            body.put("PhoneNumber", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        reqPojo.setBody(body.toString());
        reqPojo.setFormat("json");
        reqPojo.setMethod("Pisen.Service.Share.SSO.Contract.ICustomerService.AppCustomerOffset");
        reqPojo.setSessionKey(phone);
        reqPojo.setVersion("");
        reqPojo.generateAndSetSign();
        return reqPojo;
    }
}
