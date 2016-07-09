package com.pisen.baselib.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.pisen.baselib.utils.Toastor;

/**
 * @author by tanyixiu
 * @version 1.0 ,2016/2/17 10:31
 */
public class ToastorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_toastor);
    }

    public void doNormalClick(View view) {
        Toastor.build(ToastorActivity.this).text("这是默认toastor").show();
    }

    public void doLongClick(View view) {
        Toastor.build(ToastorActivity.this).text("长时间toastor").duration(Toast.LENGTH_LONG).show();
    }

    public void doShortClick(View view) {
        Toastor.build(ToastorActivity.this).text("短时间toastor").duration(Toast.LENGTH_SHORT).show();
    }

    public void doLeftClick(View view) {
        Toastor.build(ToastorActivity.this).text("左边toastor").gravity(Gravity.LEFT).show();
    }

    public void doTopClick(View view) {
        Toastor.build(ToastorActivity.this).text("上边toastor").gravity(Gravity.TOP).show();
    }

    public void doRightClick(View view) {
        Toastor.build(ToastorActivity.this).text("右边toastor").gravity(Gravity.RIGHT).show();
    }

    public void doBottomClick(View view) {
        Toastor.build(ToastorActivity.this).text("下边toastor").gravity(Gravity.BOTTOM).show();

    }

    public void doMiddleClick(View view) {
        Toastor.build(ToastorActivity.this).text("中间toastor").gravity(Gravity.CENTER).show();
    }

    public void doHideClick(View view) {
        Toastor toastor = Toastor.build(ToastorActivity.this).text("长时间toastor").gravity(Gravity.BOTTOM).duration(Toast.LENGTH_LONG).show();
        toastor.cancel(this);
    }
}
