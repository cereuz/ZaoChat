package com.zao.zaochat.admin;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zao.zaochat.R;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class GroupFragment extends Fragment {

    private RecyclerView recycler_view;
    private List<String> mData;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onezao_group0306, null);

        mContext=getActivity();
        initView(view);
        return view;
    }

    /**
     * 初始化界面
     * @param view
     */
    private void initView(View view) {

    }

}