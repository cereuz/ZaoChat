package com.zao.zaochat.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.utils.DeviceUtil;
import com.zao.zaochat.utils.ToastUtil;
import com.zao.zaochat.utils.ZaoUtils;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    TextView tv_splash;
    TextView textViewHello;
    ImageView imageView;
    ProgressBar progressBar;
    ConstraintLayout cl_layout;

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(getApplicationContext(),AdminActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        //初始化动画效果
        initAnimation();
        initData();

        mHandler.sendEmptyMessageDelayed(0,3000);
    }


    /**
     *  初始化UI界面
     */
    private void initView() {
        tv_splash = (TextView) findViewById(R.id.tv_splash);
        textViewHello = (TextView) findViewById(R.id.textViewHello);
        imageView = (ImageView)findViewById(R.id.iv_nav_header);
        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
        //进度条
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //整体布局
        cl_layout = (ConstraintLayout) findViewById(R.id.cl_layout);
    }

    /**
     *  初始化数据
     */
    private void initData() {
        //1.应用版本名称 , 版本编号 , 手机系统当前时间日期
        String versionName = DeviceUtil.getVersionName(this);

        int mVersionCode = DeviceUtil.getVersionCode(this);
        String today = ZaoUtils.getSystemTimeMore(8);
        //设置时间，日期
        textViewHello.setText(today);
        //设置版本名称
        tv_splash.setText(versionName);
    }
    /**
     * 实现启动页淡入淡出效果
     */
    private void initAnimation() {
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(4000);
        cl_layout.startAnimation(aa);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_nav_header:
                long curClickTime = System.currentTimeMillis();
                if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    finish();
                } else {
                    ToastUtil.showT(this,"请等待1秒钟后再次点击。");
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nav_header:
                startActivity(new Intent(getApplicationContext(), ZaoActivity.class));
                finish();
                break;
        }
                return false;
    }
}
