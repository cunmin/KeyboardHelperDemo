package com.littleyellow.keyboardhelper.handler;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.utils.KBSharedPreferences;
import com.littleyellow.keyboardhelper.utils.ViewUtils;

/**
 * Created by 小黄 on 2018/9/28.
 */

public class FSShowHandler implements IShowHandler{

    private final PannelView pannelView;

    private final FrameLayout frameLayout;

    private final View inputBaffle;

    private final Activity activity;

    public FSShowHandler(PannelView pannelView, FrameLayout frameLayout, View inputBaffle) {
        this.pannelView = pannelView;
        this.frameLayout = frameLayout;
        this.inputBaffle = inputBaffle;
        activity = (Activity) pannelView.getContext();
    }

    @Override
    public int showDefault() {
        int y = getTranslationY();
        pannelView.setTranslationY(y);
        inputBaffle.setVisibility(STATE_DEFAULT);
        return y;
    }

    @Override
    public void showInput() {
        if(STATE_INPUT == inputBaffle.getVisibility()){
            return;
        }
        pannelView.setTranslationY(0);
        inputBaffle.setVisibility(STATE_INPUT);
    }

    @Override
    public void showPannel() {
        pannelView.setTranslationY(0);
        inputBaffle.setVisibility(STATE_PANNEL);
    }

    @Override
    public boolean checkHeight(Rect keyboardRect,FrameLayout frameLayout) {
        boolean isChange;
        //首次弹起或者用户自己改变键盘高度
        if(keyboardRect.height()!=frameLayout.getHeight()){
            ViewUtils.initHeight(frameLayout);
            pannelView.setTranslationY(0);
            isChange = true;
        }else{
            isChange = false;
        }
        return isChange;
    }

    private int getTranslationY(){
        int kBHeight = KBSharedPreferences.getDefKeyboardHeight(pannelView.getContext());
        return  kBHeight;
    }
}
