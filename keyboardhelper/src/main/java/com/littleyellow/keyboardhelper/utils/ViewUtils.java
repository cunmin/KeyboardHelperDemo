package com.littleyellow.keyboardhelper.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 小黄 on 2018/9/18.
 */

public class ViewUtils {

    public static int initHeight(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int validPanelHeight = KBSharedPreferences.getDefKeyboardHeight(view.getContext());
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    validPanelHeight);
            view.setLayoutParams(layoutParams);
        } else {
            if(layoutParams.height != validPanelHeight){
                layoutParams.height = validPanelHeight;
                view.requestLayout();
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

}
