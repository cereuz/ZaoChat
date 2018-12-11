package com.zao.zaochat.admin;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.adapter.NotesFragmentAdapter;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.utils.ConstantValue;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.utils.ToastUtil;
import com.zao.zaochat.utils.ZaoUtils;
import com.zao.zaochat.znote.NotesDao;
import com.zao.zaochat.znote.NotesInfo;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class StatusFragment extends Fragment {

    private Context mContext;
    private List<NotesInfo>  list ;
    private NotesDao mDao;
    NotesFragmentAdapter notesFragmentAdapter;

    RecyclerView rv_notes_list;
    TextView tv_notes_list_tips;
    SwipeRefreshLayout srl_notes_list;

    TextView tv_share;
    TextView tv_delete;
    TextView tv_start;
    TextView tv_setting;
    PopupWindow popupWindow;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initUI();
            srl_notes_list.setRefreshing(false);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity();
        mDao = NotesDao.getInstance(mContext);
        View view = inflater.inflate(R.layout.fragment_notes_list, null);
        rv_notes_list = (RecyclerView)view.findViewById(R.id.rv_notes_list);
        tv_notes_list_tips = (TextView) view.findViewById(R.id.tv_notes_list_tips);
        srl_notes_list = (SwipeRefreshLayout) view.findViewById(R.id.srl_notes_list);
        initUI();
        return view;
    }


    /**
     * 初始化界面
     */
    private void initUI() {
        list = mDao.findAll(60 + "");
        LogUtil.e("查询数据库结果大小：" + list.size());
        for (NotesInfo info : list){
            LogUtil.e(info.toString());
        }

        notesFragmentAdapter = new NotesFragmentAdapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rv_notes_list.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rv_notes_list.setAdapter(notesFragmentAdapter);
        //设置增加或删除条目的动画
        rv_notes_list.setItemAnimator(new DefaultItemAnimator());
        rv_notes_list.setAdapter(notesFragmentAdapter);

        notesFragmentAdapter.setOnRItemClickListener(new NotesFragmentAdapter.OnRecyclerViewItemClickListener(){
/*            @Override
            public void onNoteClick(int position) {
                ToastUtil.showT(mContext,list.get(position).getNote());
            }

            @Override
            public void onImageClick(int position) {
                ToastUtil.showT(mContext,list.get(position).getMode());
            }

            @Override
            public void onNameClick(int position) {
                ToastUtil.showT(mContext,list.get(position).getName());
            }*/

            @Override
            public void onItemClick(View view,int position) {
                ToastUtil.showT(mContext,list.get(position).getName() + "==" +ZaoUtils.getSystemTimeMore(1,list.get(position).getTime()));
            }

            @Override
            public void onItemLongClick(View view,int position) {
//                ToastUtil.showT(mContext,list.get(position).toString());
                showPopupWindow(view);
            }
        });

        //        srlWifiList.setColorSchemeColors();  //开始用这个方法，没有用
        srl_notes_list.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        srl_notes_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.e("WifiList 刷新");
                    new RefreshNotesList().start();
//                    srl_notes_list.setRefreshing(false);
               }
            });
    }


    /**
     * 显示弹出框
     */
    private void showPopupWindow(View view) {
        View popupView = View.inflate(mContext,R.layout.popupwindow_appmanager_view,null);
        //设置透明
        ConstraintLayout cl_view = (ConstraintLayout) popupView.findViewById(R.id.cl_view);
        cl_view.getBackground().setAlpha(120);//0~255透明度值 0：全透明；255不透明

        tv_share = (TextView)popupView.findViewById(R.id.tv_share);
        tv_delete = (TextView)popupView.findViewById(R.id.tv_delete);
        tv_start = (TextView)popupView.findViewById(R.id.tv_start);
        tv_setting = (TextView)popupView.findViewById(R.id.tv_setting);

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showT(mContext,"tv_share");
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showT(mContext,"tv_delete");
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showT(mContext,"tv_start");
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showT(mContext,"tv_setting");
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });

        //透明动画（透明 -- > 不透明）
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(ConstantValue.ONE_SECOND);
        alphaAnimation.setFillAfter(true);
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0,1,
                0,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
        );
        scaleAnimation.setDuration(ConstantValue.ONE_SECOND);
        scaleAnimation.setFillAfter(true);
        //动画集合set
        AnimationSet animationSet = new AnimationSet(true);
        //添加两个动画
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        //1.创建窗体对象，指定宽高
        popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        //2.设置一个透明背景图片，如果不设值，返回键会不响应
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //3.指定窗体位置
        popupWindow.showAsDropDown(view,300,-view.getHeight() - 30);
        //执行动画集合set
        popupView.startAnimation(animationSet);
    }


    /**
     * 刷新wifi列表的线程
     */
    class RefreshNotesList extends Thread {
        @Override
        public void run() {
            try {
//                list = mDao.findAll(60 + "");
                mHandler.sendEmptyMessage(ConstantC.NOTE_LIST_REFRESHED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
