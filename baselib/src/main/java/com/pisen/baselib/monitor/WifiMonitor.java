package com.pisen.baselib.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Observable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Wifi监听器
 *
 * @author by tanyixiu
 * @version 1.0 ,2016/2/18 9:38
 */
public class WifiMonitor extends Observable<WifiMonitor.WifiStateCallback> implements IMonitor {

    public interface WifiStateCallback {
        /**
         * 网络已连接
         */
        void onConnected(WifiInfo wifiInfo);

        /**
         * 网络已断开
         */
        void onDisconnected(WifiInfo wifiInfo);
    }

    /**
     * 过滤品胜路由器
     */
    private WifiInfo wifiConfig;
    private boolean connected = false;
    private static WifiMonitor instance = null;

    private WifiMonitor() {
    }

    public static WifiMonitor getInstance() {
        if (instance == null) {
            instance = new WifiMonitor();
        }
        return instance;
    }

    @Override
    public void startMonitor(Context context) {
        context.registerReceiver(wifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void stopMonitor(Context context) {
        context.unregisterReceiver(wifiReceiver);
        unregisterAll();
    }

    public boolean isWifiConnected() {
        return connected;
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            acquireWifiState(context);
        }
    };

    private void notifyConnected(WifiInfo wifiInfo) {
        synchronized (mObservers) {
            for (WifiStateCallback observer : mObservers) {
                observer.onConnected(wifiInfo);
            }
        }
    }

    private void notifyDisconnected(WifiInfo wifiInfo) {
        synchronized (mObservers) {
            for (WifiStateCallback observer : mObservers) {
                observer.onDisconnected(wifiInfo);
            }
        }
    }

    public void acquireWifiState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            connected(context, netInfo);
        } else {
            disconnected();
        }
    }

    private void connected(Context context, NetworkInfo netInfo) {
        int netType = netInfo.getType();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            connected = true;
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiConfig = wifiManager.getConnectionInfo();
            notifyConnected(wifiConfig);
        } else {
            connected = false;
        }
    }

    private void disconnected() {
        connected = false;
        notifyDisconnected(wifiConfig);
        wifiConfig = null;
    }
}
