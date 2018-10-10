package com.littleyellow.keyboardhelper;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.littleyellow.keyboardhelper.utils.KeyboardListener;

import java.util.HashMap;

/**
 * Created by 小黄 on 2018/9/4.
 */

public class RegisterHelper {

    public static void compatInput(Activity activity, final View inputBar){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT||null==inputBar) {
            return;
        }
        KeyboardListener.assistActivity(activity, new KeyboardListener.OnInputMethodListener() {

            float priBottom = 0;

            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                if(show){
                    if(0==priBottom){
                        int[] location = new int[2];
                        inputBar.getLocationOnScreen(location);
                        priBottom= location[1]+inputBar.getHeight();
                    }
                    inputBar.setTranslationY(keyboardRect.top - priBottom);
                }else{
                    inputBar.setTranslationY(0);
                }
            }
        });
    }

    public static void compatInputPanel(Activity activity,final PannelView panel){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT||null==panel) {
            return;
        }
//        KeyboardListener.assistActivity(activity, new KeyboardListener.OnInputMethodListener() {
//
//            @Override
//            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
//                if(show){
//                    panel.setTranslationY(0);
//                }else{
//                    if(!panel.isShowPannel()){
//                        int validPanelHeight = KBSharedPreferences.getDefKeyboardHeight(panel.getContext());
//                        panel.setTranslationY(validPanelHeight);
//                    }
//                }
//            }
//        });
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
        KeyboardListener.assistFragment(fragment, new KeyboardListener.OnInputMethodListener() {
            float priBottom = 0;
            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                if(show){
                    if(0==priBottom){
                        int[] location = new int[2];
                        inputBar.getLocationOnScreen(location);
                        priBottom= location[1]+inputBar.getHeight();
                    }
                    inputBar.setTranslationY(keyboardRect.top - priBottom);
                }else{
                    inputBar.setTranslationY(0);
                }
            }
        });
    }

    public static void compatScrollInput(final Activity activity, final ScrollListener listener){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        KeyboardListener.assistActivity(activity, new KeyboardListener.OnInputMethodListener() {

            HashMap<View,Float> bottoms = new HashMap<View, Float>();

            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                Log.e("Input",show+"--->"+keyboardRect.top);
                View inputBar = null==listener?null:listener.getInputBar(activity.getCurrentFocus());
                if(null==inputBar){
                    return;
                }
                if(show){
                    float priBottom;
//                    if(bottoms.containsKey(inputBar)){
//                        priBottom = bottoms.get(inputBar);
//                    }else {
//                        int[] location = new int[2];
//                        inputBar.getLocationOnScreen(location);
//                        priBottom= location[1]+inputBar.getHeight();
//                        bottoms.put(inputBar,priBottom);
//                    }
                    int[] location = new int[2];
                    inputBar.getLocationOnScreen(location);
                    priBottom= location[1]+inputBar.getHeight();

                    float y = keyboardRect.top - priBottom;
                    if (0 != y) {
                        if(null==listener||!listener.onScroll(keyboardRect,y)){
                            inputBar.setTranslationY(y);
                        }
                    }

//                    }
                }else{
                    if(null==listener||!listener.onScroll(keyboardRect,0)){
                        inputBar.setTranslationY(0);
                    }
                }
            }
        });
    }

    public interface ScrollListener{
        boolean onScroll(Rect keyboardRect,float y);
        View getInputBar(View focusView);
    }

}
