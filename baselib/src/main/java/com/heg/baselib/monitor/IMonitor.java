package com.heg.baselib.monitor;

import android.content.Context;

/**
 * 监听器接口
 *
 * @author by tanyixiu
 * @version 1.0 ,2016/2/18 9:35
 */
public interface IMonitor {
    /**
     * 启动监听
     */
    void startMonitor(Context context);

    /**
     * 停止监听
     */
    void stopMonitor(Context context);
}
