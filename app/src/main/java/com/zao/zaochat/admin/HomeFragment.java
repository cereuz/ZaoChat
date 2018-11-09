package com.zao.zaochat.admin;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zao.zaochat.R;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.utils.ZaoUtils;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class HomeFragment extends Fragment {

    private Context mContext;
     private Handler mHandler;
     View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onezao_home0306, null);

        mContext=getActivity();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        };

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.i("onCreate ：" + ZaoUtils.getSystemTimeMore(1));
        super.onCreate(savedInstanceState);
    }
}
