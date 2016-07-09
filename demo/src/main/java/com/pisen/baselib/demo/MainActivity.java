package com.pisen.baselib.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.txt_file_util).setOnClickListener(this);
        findViewById(R.id.txt_json_parse).setOnClickListener(this);
    }

    public void doToastClick(View view) {
        startActivity(new Intent(this, ToastorActivity.class));
    }

    public void doWifiMonitorClick(View view) {
        startActivity(new Intent(this, WifiMonitorActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_file_util:
                startActivity(new Intent(this,FileUtilTestActivity.class));
                break;
            case R.id.txt_json_parse:
                startActivity(new Intent(this, JsonPTestActivity.class));
                break;
        }
    }
}
