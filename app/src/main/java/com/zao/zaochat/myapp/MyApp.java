package com.zao.zaochat.myapp;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private List<Activity> mList;//用于存放所有启动的Activity的集合

    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<Activity>();
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!mList.contains(activity)) {
            mList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity(Activity activity) {
    //判断当前集合中存在该Activity
        if (mList.contains(activity)) {
            mList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity() {
       //通过循环，把集合中的所有Activity销毁
        for (Activity activity : mList) {
           activity.finish();
        }
    }
}
