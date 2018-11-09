package com.zao.zaochat.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static boolean shoT = true;
    /**
     * 打印吐司
     * @param context  上下文环境
     * @param msg   打印文本内容
     */
    public static void showT(Context context, String msg){
       if(shoT){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
         }
    }
}
