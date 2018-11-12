package com.zao.zaochat.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author 蜗牛
 *  pathSD  :   获取SD卡路径
 */
public class ZaoUtils {
    public static final long THREE_SECOND = 3000;
    public static final long TEN_SECOND = 10000;
    public static String ONEZAO = "onezao";
    public static String logintoast = "用户名或者密码不能为空！";
    public static String loginToast2 = "登录成功！";
    public static String saveSucc = "保存成功！";
    public static String selectCB = "没有勾选CheckBox哦";
    public static String loginToastSave = "正在保存用户名和密码！";
    public static String loginToastSaveSucc = "保存用户名和密码成功！";
    public static String loginToastSaveFail = "保存用户名和密码失败！";
    public static String pathSD = Environment.getExternalStorageDirectory().getPath();
    public static String pathSD2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    public static String SELECTGENDERANDNAME = "请输入姓名并选择性别！";
    public static String DIALOG_TITLE = "版本更新";
    public static String CHECK_VERSION_JSON_URL2 = "http://www.onezao.com/zao.apk";
    public static String CHECK_VERSION_JSON_URL1 = "http://www.wanandroid.com/tools/mockapi/7828/update0725json";
    public static String CHECK_VERSION_JSON_URL = "https://coding.net/api/share/download/ff61ea71-517e-41c4-b688-7b08b832002d";
    public static int CHECKVERSIONCONTIME = 6000;


    //获取系统时间。
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeMore(int i) {
        SimpleDateFormat mFormat = null;
        Date date = new Date(System.currentTimeMillis());//获取当前时间
        switch (i) {
            case 1:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 EEEE");
                //2018年07月24日 15时16分27秒 星期二
                Log.e("CurTime", "time1=" + mFormat.format(date));
                break;

            case 2:
                mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 3:
                mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 4:
                mFormat = new SimpleDateFormat("yyyy/MM/dd");
                Log.e("CurTime", "time3=" + mFormat.format(date));
                break;
            case 5:
                mFormat = new SimpleDateFormat("HH-mm-ss");
                Log.e("CurTime", "time4=" + mFormat.format(date));
                break;
            case 6:
                mFormat = new SimpleDateFormat("EEEE");
                Log.e("CurTime", "time5=" + mFormat.format(date));
                break;
            case 7:
                mFormat = new SimpleDateFormat("E");
                Log.e("CurTime", "time6=" + mFormat.format(date));
                break;

            case 8:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 ，EEEE ");
                break;

            case 9:
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM dd, yyyy HH:mm:ss",
                        Locale.ENGLISH);
                break;
        }

        //	SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 HH:mm:ss ");
//        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 , EEEE ");
        String str = mFormat.format(date);
        return str;
    }


    public static String getSystemTimeMore(int i,long time){
        SimpleDateFormat mFormat = null;
        Date date = new Date(time);
        switch (i) {
            case 1:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 EEEE");
                //2018年07月24日 15时16分27秒 星期二
                Log.e("CurTime", "time1=" + mFormat.format(date));
                break;

            case 2:
                mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 3:
                mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 4:
                mFormat = new SimpleDateFormat("yyyy/MM/dd");
                Log.e("CurTime", "time3=" + mFormat.format(date));
                break;
            case 5:
                mFormat = new SimpleDateFormat("HH:mm:ss");
                Log.e("CurTime", "time4=" + mFormat.format(date));
                break;
            case 6:
                mFormat = new SimpleDateFormat("EEEE");
                Log.e("CurTime", "time5=" + mFormat.format(date));
                break;
            case 7:
                mFormat = new SimpleDateFormat("E");
                Log.e("CurTime", "time6=" + mFormat.format(date));
                break;

            case 8:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 ，EEEE ");
                break;

            case 9:
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM dd, yyyy HH:mm:ss",
                        Locale.ENGLISH);
                break;
            case 10:
                mFormat = new SimpleDateFormat("HH:mm:ss");
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                Log.e("CurTime", "time4=" + mFormat.format(date));
                break;
        }

        //	SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 HH:mm:ss ");
//        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 , EEEE ");
        String str = mFormat.format(date);
        return str;
    }

    //时间转换
    public static String tranTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(Long.valueOf(time));//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //时间转换
    public static String  tranTime(int i,String time) {
        SimpleDateFormat formatter = null;
        Date curDate = new Date(Long.valueOf(time));//获取当前时间
        switch (i) {
            case 1:
                formatter = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
                break;
            case 2:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
        }
        String str = formatter.format(curDate);
        return str;
    }

    //时间转换
    public static String  tranTime(int i,Date date) {
        SimpleDateFormat formatter = null;
//        Date curDate = new Date(Long.valueOf(time));//获取当前时间
        switch (i) {
            case 1:
                formatter = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
                break;
            case 2:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
        }
        String str = formatter.format(date);
        return str;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     * //2018年07月24日 15时16分27秒 星期二
     * SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 EEEE");
     *
     * @param date
     * @return
     */
    public static String getDateToString(String date) {
        long lcc = Long.valueOf(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 EEEE");
        String times = sdf.format(new Date(lcc));
        return times;
    }


    //复制文件
    public static void copyFile(String path1, String path2) {
        // TODO Auto-generated method stub
        File file2 = new File(path2);
        if (!file2.getParentFile().exists()) {
            boolean result = file2.getParentFile().mkdirs();
            if (!result) {
                Log.i(ConstantValue.TAG, "文件夹创建失败！！！");
            }
        }

        //封装数据源
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(path1));
            //封装目的地
            FileOutputStream fos = new FileOutputStream(file2);

            int by = 0;
            while ((by = fis.read()) != -1) {
                fos.write(by);
            }
            //释放资源(先关谁都行)
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件
    public static String readFile(String path) {
        File file = new File(path);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String content = "";
            StringBuilder sb = new StringBuilder();
            while (content != null) {
                content = bf.readLine();
                if (content == null) {
                    break;
                }
                sb.append(content.trim());
            }
            bf.close();
            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    //睡眠
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到传入的地址的可用大小
     *
     * @param path
     * @return 路径：/storage/sdcard1
     * 内存大小：-367984640.00 B
     */
    public static String getPathSize(Context context, String path) {
        StatFs fs = new StatFs(path);
        // 拿到可用区块的数量
        int blocks = fs.getAvailableBlocks();
        // 拿到每个区块的大小
        int size = fs.getBlockSize();

        return Formatter.formatFileSize(context, Long.parseLong(blocks * size + ""));
    }


    /**
     * 根据文件路径，打印其下边的所有文件和文件夹
     *
     * @param path
     * @return
     */
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                Log.e(ConstantValue.TAG, "文     件：" + tempList[i]);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
                Log.e(ConstantValue.TAG, "文件夹：" + tempList[i]);
            }
        }
        return files;
    }


    /**
     * 根据文件路径，打印其下边的所有文件和文件夹(包括其子文件夹)
     *
     * @param file
     * @return
     */
    public static void walk(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // 列出全部的文件
                File f[] = file.listFiles();
                if (f != null)
                    for (int i = 0; i < f.length; i++){
                        if (f[i].isDirectory()) {
                            Log.e(ConstantValue.TAG, "文件夹 ：" + f[i]);
                        }
                        //递归调用自身
                        walk(f[i]);
                    }
            } else {
                // 输出路径
//                System.out.println(file);
                Log.e(ConstantValue.TAG, "文件 ：" + file);
            }
        }
    }
}

