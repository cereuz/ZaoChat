package com.zao.zaochat.myapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zao.zaochat.utils.ToastUtil;

public class BaseActivity extends AppCompatActivity {
    private MyApp application;
    private BaseActivity mContext;

    //动态申请权限
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    final String positive = "获取权限" ;
    final String negative = "退出";

    private String[] permission = new String[]{
/*
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.CLEAR_APP_CACHE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (application == null) {
          // 得到Application对象
          application = (MyApp) getApplication();
        }
        mContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法

        initPermission();
    }

    /**
     * 初始化权限 , 传递参数，如果为true，就需要执行检查权限
     */
    private void initPermission() {
            if (Build.VERSION.SDK_INT >= 23) {
                checkBasePermission();
            } else {
                return;
            }
    }

    /**
     * 初始化权限
     */
    private void checkBasePermission() {
            /**
             * 第 1 步: 检查是否有相应的权限
             */
            boolean isAllGranted = checkPermissionAllGranted(
                    permission
            );
            // 如果这3个权限全都拥有, 则直接执行读取短信代码
            if (isAllGranted) {
/*            getData();
            simpleAdapter.notifyDataSetChanged();*/
                ToastUtil.showT(mContext,"所有权限已经授权！");
                return;
            }

            /**
             * 第 2 步: 请求权限
             */
            // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
            ActivityCompat.requestPermissions(
                    mContext,
                    permission,
                    MY_PERMISSION_REQUEST_CODE
            );
        }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                ToastUtil.showT(mContext,"检查权限");
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行读取短信代码
/*                getData();
                simpleAdapter.notifyDataSetChanged();*/
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//                openAppDetails();
                ToastUtil.showT(mContext,"需要授权！");
                myPermissionDialog();
            }
        }
    }

    /**
     * 点击按钮，弹出一个普通对话框
     */
    private void myPermissionDialog() {
        // 通过builder 构建器来构造
        final AlertDialog.Builder   builder =  new AlertDialog.Builder(this);
        builder.setTitle("警告zz");
        builder.setMessage("世界上最遥远的距离是没有网络！");
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了确定按钮");
                Toast.makeText(mContext, positive, Toast.LENGTH_SHORT).show();
                initPermission();
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了取消按钮");
                Toast.makeText(mContext, negative, Toast.LENGTH_SHORT).show();
                mContext.finish();
            }
        });
        builder.setCancelable(false);  //设置获取权限框不可以取消。
        //最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }


    // 添加Activity方法
    public void addActivity() {
        application.addActivity(mContext);// 调用MyApp的添加Activity方法
    }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity(mContext);// 调用MyApp的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity();// 调用MyApp的销毁所有Activity方法
    }

}
