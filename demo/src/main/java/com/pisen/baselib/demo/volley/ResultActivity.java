package com.pisen.baselib.demo.volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pisen.baselib.demo.R;

/**
 * @author hegang
 * @version 1.0, 2016/2/22 11:21
 */
public class ResultActivity extends Activity {

    private static final String param = "param";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String result = getIntent().getStringExtra(param);
        TextView txtView = (TextView) findViewById(R.id.result_txt);
        txtView.setText(result);
    }

    public static void startSelf(String result, Context context){
        context.startActivity(new Intent(context,ResultActivity.class).putExtra(param,result));
    }
}
