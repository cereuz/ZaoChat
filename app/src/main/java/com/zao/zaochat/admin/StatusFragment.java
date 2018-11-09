package com.zao.zaochat.admin;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zao.zaochat.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class StatusFragment extends Fragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onezao_status0306, null);

        mContext=getActivity();
        initUI(view);
        return view;
    }


    /**
     * 初始化界面
     * @param view
     */
    private void initUI(View view) {
    }
}
