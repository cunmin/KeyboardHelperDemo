package com.littleyellow.keyboardhelper;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 小黄 on 2018/9/4.
 */

public class RegisterHelper {

    public static void compatInput(Activity activity, final View inputBar){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT||null==inputBar) {
            return;
        }
        InputMethodHelper.assistActivity(activity, new InputMethodHelper.OnInputMethodListener() {
            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                if(show){
                    int[] location = new int[2];
                    inputBar.getLocationOnScreen(location);
                    float priBottom = location[1]+inputBar.getHeight();
                    int top = keyboardRect.top;
                    if(priBottom > top) {
                        inputBar.setTranslationY(top - priBottom);
                    }
                }else{
                    inputBar.setTranslationY(0);
                }
            }
        });
    }

    /**
     * 要在Activity布局完成再设置Fragment,如
     * mViewPager.post(new Runnable() {
        @Override
        public void run() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),titles);
        mViewPager.setAdapter(adapter);
        }
        });
     * @param fragment
     * @param inputBar
     */
    public static void compatInput(Fragment fragment, final View inputBar){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT||null==inputBar) {
            return;
        }
        InputMethodHelper.assistFragment(fragment, new InputMethodHelper.OnInputMethodListener() {
            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                if(show){
                    int[] location = new int[2];
                    inputBar.getLocationOnScreen(location);
                    float priBottom = location[1]+inputBar.getHeight();
                    int top = keyboardRect.top;
                    if(priBottom > top) {
                        inputBar.setTranslationY(top - priBottom);
                    }
                }else{
                    inputBar.setTranslationY(0);
                }
            }
        });
    }

}
