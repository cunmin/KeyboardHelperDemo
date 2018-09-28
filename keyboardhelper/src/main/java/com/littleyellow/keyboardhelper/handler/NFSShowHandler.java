package com.littleyellow.keyboardhelper.handler;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.utils.KBSharedPreferences;
import com.littleyellow.keyboardhelper.utils.ViewUtils;

/**
 * Created by 小黄 on 2018/9/28.
 */

public class NFSShowHandler implements IShowHandler{

    private final PannelView pannelView;

    private final View inputBaffle;

    public NFSShowHandler(PannelView pannelView,View inputBaffle) {
        this.pannelView = pannelView;
        this.inputBaffle = inputBaffle;
    }

    @Override
    public void showDefault() {
        int y = KBSharedPreferences.getDefKeyboardHeight(pannelView.getContext());
        pannelView.setTranslationY(y);
        inputBaffle.setVisibility(STATE_DEFAULT);
    }

    @Override
    public void showInput() {
        if(STATE_INPUT == inputBaffle.getVisibility()){
            return;
        }
        int y = KBSharedPreferences.getDefKeyboardHeight(pannelView.getContext());
        pannelView.setTranslationY(y);
        inputBaffle.setVisibility(STATE_INPUT);
    }

    @Override
    public void showPannel() {
        pannelView.setTranslationY(0);
        inputBaffle.setVisibility(STATE_PANNEL);
    }

    @Override
    public boolean checkHeight(Rect keyboardRect,FrameLayout frameLayout) {
        int keyHeight = keyboardRect.height();
        int frameHeight = frameLayout.getHeight();
        if(keyboardRect.height()!=frameLayout.getHeight()){

            ViewUtils.initHeight(frameLayout);
            pannelView.setTranslationY(0);
            return true;
        }else{
            return false;
        }
    }
}
