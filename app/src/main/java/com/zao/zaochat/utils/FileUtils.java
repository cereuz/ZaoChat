package com.zao.zaochat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.zao.zaochat.R;
import com.zao.zaochat.model.FileBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileUtils {

    public static String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ame" + "/";
    public static String rootDir2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Unicom" + "/";
    /**
     * FileProvider的  FILE_PROVIDER_AUTHORITIES
     */
    public static final String FILE_PROVIDER_AUTHORITIES = "com.onezao.zao.zaov.fileprovider";

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }


    /**
     *  手动以指定的宽高获取
     *     作者：tablle
     *     缺点：比较耗时
     *     原文：https://blog.csdn.net/tablle/article/details/51698884
     * @param imagePath
     * @param width
     * @param height
     */
    public static Bitmap getImageThumbnail(String imagePath,int width,int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap img_bitmap = null;
        //节约内存
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;/*设置让解码器以最佳方式解码*/
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = true;
        //If diTher is true, the decoder will attempt to dither the decoded image
        options.inDither = false;//不进行图片抖动处理
        // 获取这个图片的宽和高，注意此处的bitmap为null
        img_bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;//设为 false

        //计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        img_bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象

        img_bitmap = ThumbnailUtils.extractThumbnail(img_bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return img_bitmap;
    }



    /**
     * 获取保存文件的文件夹
     * @return
     */
    public static String getSaveDirectory(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ame" + "/";
            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            Toast.makeText(context, rootDir, Toast.LENGTH_SHORT).show();

            return rootDir;
        } else {
            return null;
        }
    }

    /**
     * 获取保存文件的文件夹
     * @param context
     * @param rootDir
     * @return
     */
    public static String getSaveDirectory(Context context,String rootDir) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Zneo" + "/";
            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            Toast.makeText(context, rootDir, Toast.LENGTH_SHORT).show();

            return rootDir;
        } else {
            return null;
        }
    }


    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(Context context,String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            Toast.makeText(context, "删除文件失败:" + delFile + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(context,delFile);
            else
                return deleteDirectory(context,delFile);
        }
    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(Context context,String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Toast.makeText(context, "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(Context context,String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Toast.makeText(context, "删除目录失败：" + filePath + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(context,file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(context,file.getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Toast.makeText(context, "删除目录失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            Toast.makeText(context, "删除目录：" + filePath + "失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /**
     * 获取当前目录下所有的文件
     * @param context
     * @param fileAbsolutePath
     * @return
     */
    public static ArrayList<FileBean> GetFileAttr(Context context, String fileAbsolutePath) {
        ArrayList<FileBean> list = new ArrayList<FileBean>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                FileBean fileBean = new FileBean();
                String filename = subFile[iFileLength].getName();
                String path = subFile[iFileLength].getAbsolutePath();
                String space = Formatter.formatFileSize(context,getFileSize(path));
                long date = subFile[iFileLength].lastModified();//文件最后修改时间
//                Bitmap bitmap = getVideoThumb(path);
                Bitmap bitmap = getFileBitmap(context,path);
                LogUtil.e("文件名称 ：" + filename + "\n" + "文件路径 ：" + path + "\n" + "最后修改时间 ：" + ZaoUtils.tranTime(2,new Date(date)) + "\n" + "文件大小 ：" + space + "\n" + "Bitmap : " + bitmap);

                fileBean.setFilename(filename);
                fileBean.setPath(path);
                fileBean.setSize(space);
                fileBean.setDate(date);
                fileBean.setBitmap(bitmap);
/*                Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                fileBean.setBitmap(bitmap2);*/
                list.add(fileBean);
/*                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(".mp4")) {
                    list.add(fileBean);
                }*/
            }
        }
        return list;
    }

    /**
     * 获取 指定路径下文件的  bitmap
     * @return
     */
    private static Bitmap getFileBitmap(Context context,String filePath) {
        String path = filePath.toLowerCase();
        if(path.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".bmp") ){
            return getImageThumbnail(filePath,40,40);
        } else if (path.endsWith(".mp4") || path.endsWith(".rmvb") || path.endsWith(".avi") || path.endsWith(".mkv")) {
            return getVideoThumb(filePath);
        } else if (path.endsWith(".mp3") || path.endsWith(".wma") || path.endsWith(".flac") || path.endsWith(".aac") || path.endsWith(".ogg") || path.endsWith(".amr")) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_music);
        } else {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_file);
        }
    }


    /**
     * 获取指定文件大小
     * @param f
     * @return
     * @throws Exception 　　
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取指定文件大小
     * @param f
     * @return
     * @throws Exception 　　
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
}
