package com.zao.zaochat.WifiTool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.zao.zaochat.utils.LogUtil;

/**
 * 项目名称：HotChat2
 * 类描述：
 * 创建人：qq985
 * 创建时间：2016/12/13 20:59
 * 修改人：qq985
 * 修改时间：2016/12/13 20:59
 * 修改备注：
 */
public class WifiReceiver extends BroadcastReceiver {

    private OnWifiChangedListener onWifiChangedListener;

    @Override
    public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                //signal strength changed
            } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否
                LogUtil.i("网络状态改变");
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                    LogUtil.i("wifi网络连接断开");
                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    //获取当前wifi名称
                    LogUtil.e("连接到网络 （ZNEO） ： " + wifiInfo.getSSID());

                }

            } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {//wifi打开与否
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

                if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                    LogUtil.i("系统关闭wifi");
                    if (onWifiChangedListener != null) {
                        onWifiChangedListener.onWifiClosed();
                    }
                } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                    LogUtil.i("系统开启wifi");
                    if (onWifiChangedListener != null) {
                        onWifiChangedListener.onWifiOpened();
                    } else {
                        LogUtil.e("onWifiChangedListener为空");
                    }
                }
            }
    }

    public interface OnWifiChangedListener {
        void onWifiOpened();

        void onWifiClosed();
    }

    public void setOnWifiChangedListener(OnWifiChangedListener onWifiChangedListener) {
        this.onWifiChangedListener = onWifiChangedListener;
    }
}