package com.littleyellow.inputdialog.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by 小黄 on 2018/9/18.
 */

public class ViewUtils {

    public static int initHeight(View view){
        return initHeight(view,0);
    }

    public static int initHeight(View view,int forceHeight){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int validPanelHeight = forceHeight>0?forceHeight:KBSharedPreferences.getKeyboardHeight(view.getContext());
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    validPanelHeight);
            view.setLayoutParams(layoutParams);
        } else {
            if(layoutParams.height != validPanelHeight){
                layoutParams.height = validPanelHeight;
                view.requestLayout();
//                view.setLayoutParams(layoutParams);
            }
        }
        return validPanelHeight;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowHeight(Activity activity){
        final ViewGroup decorView = (ViewGroup) activity.findViewById(android.R.id.content);
        int windowHeight = decorView.getBottom();
        return windowHeight;
    }

    public static int getScreenRealHeight(Context context){
        WindowManager manager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        if(Build.VERSION.SDK_INT < 17) {
            display.getSize(point);
        } else {
            display.getRealSize(point);
        }
        int height = point.y;
        return height;
    }
}
