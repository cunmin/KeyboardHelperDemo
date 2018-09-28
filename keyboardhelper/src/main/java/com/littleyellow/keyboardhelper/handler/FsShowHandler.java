package com.littleyellow.keyboardhelper.handler;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import com.littleyellow.keyboardhelper.utils.ViewUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 小黄 on 2018/9/28.
 */

public class FSShowHandler implements IShowHandler{

    private final FrameLayout frameLayout;

    private final View inputBaffle;

    public FSShowHandler(FrameLayout frameLayout,View inputBaffle) {
        this.frameLayout = frameLayout;
        this.inputBaffle = inputBaffle;
    }

    @Override
    public void showDefault() {
        frameLayout.setVisibility(GONE);
        inputBaffle.setVisibility(STATE_DEFAULT);
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
