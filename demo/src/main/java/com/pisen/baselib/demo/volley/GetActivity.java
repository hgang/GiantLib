package com.pisen.baselib.demo.volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.manager.HttpCallback;
import com.android.volley.manager.LoadController;
import com.android.volley.manager.RequestManager;
import com.pisen.baselib.demo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hegang
 * @version 1.0, 2016/2/18 11:52
 */
public class GetActivity extends Activity {

    private LoadController mController;
    private int actionId = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        findViewById(R.id.get_btn_execute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtUrl = (EditText)findViewById(R.id.get_edt_url);
                EditText edtParam = (EditText)findViewById(R.id.get_edt_param_name);
                EditText edtParamValue = (EditText)findViewById(R.id.get_edt_param_value);
                Map map = new HashMap();
                map.put(edtParam.getText().toString(),edtParamValue.getText().toString());
                String url = edtUrl.getText().toString();
                mController = RequestManager.getInstance().get(url).addParams(map).setActionId(actionId)
                        .addCallback(new HttpCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String response, int actionId) {
                        Log.d("hegang","; response = "+response+"; actionId = "+actionId);
                        ResultActivity.startSelf(response,GetActivity.this);
                    }
                }).execute();
            }
        });
    }

    private void testVolley(){
//        VolleyClient<Cache> client =VolleyClient.getInstance();
        User u = new User();
    }

    class User{
        int age;
        String name;
    }

    class UserResponse{
        String response;
        boolean isError;
    }

    @Override
    protected void onDestroy() {
        //two ways of cancel requests:
        if (mController != null)
            mController.cancel();
        // another way
        //RequestManager.getInstance().cancel(actionId);
        super.onDestroy();
    }
}
