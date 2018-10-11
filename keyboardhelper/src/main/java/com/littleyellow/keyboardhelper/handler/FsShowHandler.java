package com.littleyellow.keyboardhelper.handler;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.utils.ViewUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
        frameLayout.setVisibility(GONE);
        inputBaffle.setVisibility(STATE_DEFAULT);
        return frameLayout.getHeight();
    }

    @Override
    public void showInput() {
        if(STATE_INPUT == inputBaffle.getVisibility()){
            return;
        }
        frameLayout.setVisibility(VISIBLE);
        inputBaffle.setVisibility(STATE_INPUT);
    }

    @Override
    public void showPannel() {
        frameLayout.setVisibility(VISIBLE);
        inputBaffle.setVisibility(STATE_PANNEL);
    }

    @Override
    public boolean checkHeight(Rect keyboardRect,FrameLayout frameLayout) {
        if(keyboardRect.height()!=frameLayout.getHeight()){
            ViewUtils.initHeight(frameLayout);
            return true;
        }else{
            return false;
        }
    }
}
