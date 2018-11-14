package com.zao.zaochat.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zao.zaochat.R;
import com.zao.zaochat.adapter.FileReceiveAdapter;
import com.zao.zaochat.listener.OnItemClickListener;
import com.zao.zaochat.model.FileBean;
import com.zao.zaochat.utils.ConstantValue;
import com.zao.zaochat.utils.FileUtils;
import com.zao.zaochat.utils.OpenFileUtil;
import com.zao.zaochat.utils.ScreenUtils;
import com.zao.zaochat.utils.ToastUtil;
import com.zao.zaochat.utils.ZaoUtils;

import java.util.ArrayList;
import java.util.Date;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {

    private FileReceiveAdapter mAdapter;
    ArrayList<FileBean> list;
    RecyclerView rv_record_video;
    int mPosition;

    TextView tv_share;
    TextView tv_delete;
    TextView tv_start;
    TextView tv_setting;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_file);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 初始化界面内容
     */
    private void initView() {
        list = FileUtils.GetFileAttr(this, FileUtils.getSaveDirectory(this));
        list.addAll(FileUtils.GetFileAttr(this, FileUtils.getSaveDirectory(this, FileUtils.rootDir2)));
        for(FileBean file : list){
            Log.e(ConstantValue.TAG,"文件名称 ：" + file.getFilename() + "\n" + "文件路径 ：" + file.getPath() + "\n" + "最后修改时间 ：" + ZaoUtils.tranTime(2,new Date(file.getDate())) + "\n" + "文件大小 ：" + file.getSize() + "\n" + "Bitmap : " + file.getBitmap());
        }

        rv_record_video = (RecyclerView)findViewById(R.id.rv_record_video);
        mAdapter = new FileReceiveAdapter(this, list);
        rv_record_video.setLayoutManager(new LinearLayoutManager(this));
//        rv_record_video.addItemDecoration(new LineDecoration(32,1,this.getResources().getColor(R.color.colorPrimary)));
        rv_record_video.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileBean tag =(FileBean)view.getTag();
                ToastUtil.showT(getApplicationContext(),list.get(position).getFilename() + "\n" + tag.getFilename());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"TestItemOnLongClick", Toast.LENGTH_SHORT).show();
                mPosition = position;
                //普通的弹出框
//                showPopMenu(view);
                //动画效果的弹出框
                showPopupWindow(view);
            }
        });

        //设置ImageView的点击事件
        mAdapter.setOnItemImageViewClickListener(new FileReceiveAdapter.ItemImageViewInterface(){
            @Override
            public void onclick(View view, int position) {
                Toast.makeText(getApplicationContext(),"点击了小图标 : " + position ,Toast.LENGTH_SHORT).show();
/*                //从数据库中删除数据
                mAppInfoList.delete(mAppInfoList.get(position).getPhone());
                //从集合中删除数据
                mAppInfoList.remove(position);
                //4.通知适配器，数据改变了。
                mAdapter.notifyDataSetChanged();*/
            }
        });
    }



    /**
     * 显示弹出框
     */
    private void showPopupWindow(View view) {
        View popupView = View.inflate(this,R.layout.popupwindow_file_receive_view,null);
        //设置透明
        ConstraintLayout cl_view = (ConstraintLayout) popupView.findViewById(R.id.cl_view);
        cl_view.getBackground().setAlpha(120);//0~255透明度值 0：全透明；255不透明

        tv_share = (TextView)popupView.findViewById(R.id.tv_send);
        tv_delete = (TextView)popupView.findViewById(R.id.tv_share);
        tv_start = (TextView)popupView.findViewById(R.id.tv_file_open);
        tv_setting = (TextView)popupView.findViewById(R.id.tv_store);

        tv_share.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_delete.setText("删除");
        tv_start.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

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
/*        //3.指定窗体位置
        popupWindow.showAsDropDown(view,300,-view.getHeight() - 50);*/
        //3.指定窗体位置
        int windowPos[] = ScreenUtils.calculatePopWindowPos(view, popupView,400,80);
/*        int xOff = 20;  // 可以自己调整偏移
        windowPos[0] -= xOff;
        int yOff = 300;
        windowPos[1] -= yOff;*/
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        //执行动画集合set
        popupView.startAnimation(animationSet);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_file_open:
                String path = list.get(mPosition).getPath();
                startActivity(OpenFileUtil.openFile(this,path));
                break;
            case R.id.tv_share:
/*                FileUtils.delete(this,list.get(mPosition).getPath());
                ToastUtil.showT(getApplicationContext(),"tv_delete ： 删除成功！");
                handler.sendEmptyMessage(2);*/
                break;

            case R.id.tv_send:
                ToastUtil.showT(this,"分享");
                //发送信息发送意图给短信app
                intent = new Intent(Intent.ACTION_SEND);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType("*/*");//多个文件格式
                //信息内容
                intent.putExtra(Intent.EXTRA_TEXT, "好消息，好消息[ "
                        + "Zao ]非常好用，你值得拥有，下载地址:http://www.zao.com/app/");
                startActivity(intent);
                break;

            case R.id.tv_store:
                ToastUtil.showT(getApplicationContext(),"tv_setting");
                break;
        }
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }
}
