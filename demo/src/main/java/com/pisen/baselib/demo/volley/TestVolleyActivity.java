package com.pisen.baselib.demo.volley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pisen.baselib.demo.R;

/**
 * @author hegang
 * @version 1.0, 2016/2/18 11:45
 */
public class TestVolleyActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_volley);
        findViewById(R.id.test_btn_get).setOnClickListener(this);
        findViewById(R.id.test_btn_post).setOnClickListener(this);
        findViewById(R.id.test_btn_Json).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_btn_get:
                startActivity(new Intent(this,GetActivity.class));
                break;
            case R.id.test_btn_post:
                startActivity(new Intent(this,PostActivity.class));
                break;
            case R.id.test_btn_Json:
                break;
        }
    }
}
