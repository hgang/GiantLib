package com.pisen.baselib.demo;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pisen.baselib.monitor.WifiMonitor;

public class WifiMonitorActivity extends Activity implements WifiMonitor.WifiStateCallback {

    private TextView mTextView;
    private WifiMonitor mWifiMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_monitor);
        mTextView = (TextView) findViewById(R.id.demo_wifimonitor_txt_msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnected(WifiInfo wifiInfo) {
        mTextView.append("\r\n");
        mTextView.append("wifi已连接");
    }

    @Override
    public void onDisconnected(WifiInfo wifiInfo) {
        mTextView.append("\r\n");
        mTextView.append("wifi已断开");
    }

    public void doStartClick(View view) {
        initMonitor();
    }

    public void doStopClick(View view) {
        destroyMonitor();
    }

    private void initMonitor() {
        mWifiMonitor = WifiMonitor.getInstance();
        mWifiMonitor.startMonitor(this);
        mWifiMonitor.registerObserver(this);
    }

    private void destroyMonitor() {
        if (null == mWifiMonitor) {
            return;
        }
        mWifiMonitor.unregisterObserver(this);
        mWifiMonitor.stopMonitor(this);
    }
}
