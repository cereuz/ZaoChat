package com.zao.zaochat.utils;

import android.content.Context;
import android.view.View;

public class ScreenUtils {

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        //判断需要在左侧显示，还是右侧显示，需要考虑头像的宽度。
        final boolean isNeedShowLeft = (screenWidth - anchorLoc[0] - anchorWidth < (windowWidth + 80));
        if (isNeedShowUp) {
            windowPos[1] = anchorLoc[1] - windowHeight + 50;
            if(isNeedShowLeft){
                windowPos[0] = anchorLoc[0] + anchorWidth - windowWidth - 30 ;
            } else {
                windowPos[0] = anchorLoc[0] + 30 ;
            }
        } else {
            windowPos[1] = anchorLoc[1] + anchorHeight - 20;
            if(isNeedShowLeft){
                windowPos[0] = anchorLoc[0] + anchorWidth - windowWidth - 30 ;
            } else {
                windowPos[0] = anchorLoc[0] + 30 ;
            }
        }
        return windowPos;
    }
}
