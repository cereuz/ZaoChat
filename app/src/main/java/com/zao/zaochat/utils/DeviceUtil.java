package com.zao.zaochat.utils;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class DeviceUtil {
    private static WifiManager wifi;

    private String getInfo(Context context) {
        wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();

        String maxText = info.getMacAddress();
        String ipText = intToIp(info.getIpAddress());
        String status = "";
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            status = "WIFI_STATE_ENABLED";
        }
        String ssid = info.getSSID();
        int networkID = info.getNetworkId();
        int speed = info.getLinkSpeed();
        String bssid = info.getBSSID();
        String IMEI = getIMEI2(context);
        String androidId  = getAndroidId(context);
        String SERIAL = getSerialNumber();
        String deviceName = getDeviceName();
        String BlueTooth = getBT();
        return "mac ：" + maxText + "\n\r"
                + "ip ：" + ipText + "\n\r"
                + "wifi status ：" + status + "\n\r"
                + "ssid ：" + ssid + "\n\r"
                + "net work id ：" + networkID + "\n\r"
                + "connection speed：" + speed + "\n\r"
                + "BSSID ：" + bssid + "\n\r"
                + "IMEI ：" + IMEI + "\n\r"
                + "androidId ：" + androidId + "\n\r"
                + "SERIAL ：" + SERIAL + "\n\r"
                + "deviceName ：" + deviceName + "\n\r"
                + "BlueTooth ：" + BlueTooth + "\n\r"
                ;
    }

    public static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 24) & 0xFF);
    }

    public static StringBuilder obtainWifiInfo(Context context) {

        // 显示扫描到的所有wifi信息：
        wifi = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            StringBuilder scanBuilder = new StringBuilder();
            List<ScanResult> scanResults = wifi.getScanResults();//搜索到的设备列表

            for (ScanResult scanResult : scanResults) {
                scanBuilder.append("\n设备名：" + scanResult.SSID
                        + "\n信号强度：" + wifi.calculateSignalLevel(scanResult.level, 1001)
                        + "\nBSSID:" + scanResult.BSSID);
            }
            return scanBuilder;
        }
            return null;
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        String deviceId = tm.getDeviceId();
        Log.e("DEVICE_ID ", deviceId + " ");
        return deviceId;
    }

    public static String getIMEI2(Context context) {
        String imei = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int slot = 0; slot < tm.getPhoneCount(); slot++) {
                imei  += tm.getDeviceId(slot) + "---";
            }

        }  else {
            imei = tm.getDeviceId();
        }
        return imei;
    }
    public static String getAndroidId(Context context){
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        Log.e("ANDROID_ID", androidId + " ");
        return androidId;
    }

    public String getSerialNumber(){
        return android.os.Build.SERIAL;
    }

    public static String getDeviceName(){
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        return myDevice.getName();
//        return myDevice.getName() + "===" + android.os.Build.DEVICE;
    }

    public static String getBT(){
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return m_BluetoothAdapter.getAddress();

    }


    //获取版本名称
    public static String getVersionName(Context context) {
        //1.包管理者对象 PackageManager
        PackageManager pm = context.getPackageManager();
        //2. 从包的管理者对象中，获取指定包名的基本信息（版本名称，版本号）,flag传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),0);
//            return packageInfo.versionName + "\n" + packageInfo.packageName;
            return packageInfo.versionName ;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //获取版本号
    public static int getVersionCode(Context context) {
        //1.包管理者对象 PackageManager
        PackageManager pm = context.getPackageManager();
        //2. 从包的管理者对象中，获取指定包名的基本信息（版本名称，版本号）,flag传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),0);
//            return packageInfo.versionName + "\n" + packageInfo.packageName;
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  0;
    }
}
