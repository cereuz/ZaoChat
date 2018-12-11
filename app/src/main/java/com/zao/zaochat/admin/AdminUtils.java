package com.zao.zaochat.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import android.widget.Toast;

import com.zao.zaochat.R;
import com.zao.zaochat.activity.CreateRoomActivity;
import com.zao.zaochat.activity.FileActivity;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.utils.ToastUtil;
import com.zao.zaochat.utils.ZaoUtils;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class AdminUtils {
    public static boolean AdminMenu(Context context , MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            LogUtil.i("发起聊天室。。。");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                ToastUtil.showT(context,"Android 8.0以上暂时不支持创建聊天室！");
            } else {
                Intent  intent  =  new Intent(context,CreateRoomActivity.class);
                context.startActivity(intent);
            }
            return true;
        } else  if (id == R.id.action_file) {
            LogUtil.i("接收到的文件。。。");
 			Intent  intent  =  new Intent(context,FileActivity.class);
 			context.startActivity(intent);
            return true;
        } else  if (id == R.id.action_diary) {
            LogUtil.i("日记。。。");
            Toast.makeText(context, "自动添加测试数据", Toast.LENGTH_SHORT).show();
            ZaoUtils.addNotesData(context);
/* 			Intent  intent  =  new Intent(context,TestMeActivity.class);
 			context.startActivity(intent);*/
            return true;
        }  else  if (id == R.id.action_query) {
            LogUtil.i("查询。。。");
/* 			Intent  intent  =  new Intent(context,TestMeActivity.class);
 			context.startActivity(intent);*/
            return true;
        }  else  if (id == R.id.action_settings) {
            LogUtil.i("设置。。。");
 /*			Intent  intent  =  new  Intent(this,SettingActivity.class);
 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.action_about) {
            LogUtil.i("关于。。。");
 /*	    Intent  intent  =  new  Intent(this,AboutActivity.class);
 			startActivity(intent);*/
            return true;
        }  else  if (id == android.R.id.home) {
            LogUtil.i("主页,home");
 			/*Intent  intent  =  new  Intent(this,AboutActivity.class);
 			startActivity(intent);*/
            //finish()方法目前是在Activity才有的方法，需要使用Actitvity强转一下。
            ((Activity)context).finish();
            return true;
        }  else  if (id ==  R.id.action_search) {
            LogUtil.i("搜索一下。。。");
            return true;
        }  else  if (id ==  R.id.action_search2) {
            LogUtil.i("搜索一下【2】。。。");
            return true;
        }
        return false;
    }
}
