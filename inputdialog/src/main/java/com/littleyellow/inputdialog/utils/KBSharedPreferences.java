package com.littleyellow.inputdialog.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by 小黄 on 2018/9/18.
 */

public class KBSharedPreferences {

    private static final String EXTRA_DEF_KEYBOARD_HEIGHT = "DEF_KEYBOARD_HEIGHT";

    public static final int DEF_PANNEL_HEAGH_DP = 220;
    private static int deyboardHeight = -1;

    private static int pannelHeight = -1;

    public static int getPannelHeight(Context context) {
        if (pannelHeight < 0) {
            pannelHeight = dip2px(context, DEF_PANNEL_HEAGH_DP);
        }
        pannelHeight = Math.max(pannelHeight,deyboardHeight);
        return pannelHeight;
    }

    public static int getKeyboardHeight(Context context){
        if(deyboardHeight < 0){
            deyboardHeight = PreferenceManager.getDefaultSharedPreferences(context).getInt(EXTRA_DEF_KEYBOARD_HEIGHT, 0);
        }
        return deyboardHeight;
    }

    public static void setDefKeyboardHeight(Context context, int height) {
        if (deyboardHeight != height&&0<=height) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(EXTRA_DEF_KEYBOARD_HEIGHT, height).commit();
            deyboardHeight = height;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
