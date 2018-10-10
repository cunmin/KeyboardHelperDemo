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

public class NFSShowHandler implements IShowHandler{

    private final PannelView pannelView;

    private final View inputBaffle;

    private final Activity activity;

    private final FrameLayout frameLayout;

    public NFSShowHandler(PannelView pannelView,FrameLayout frameLayout,View inputBaffle) {
        this.pannelView = pannelView;
        this.frameLayout = frameLayout;
        this.inputBaffle = inputBaffle;
        activity = (Activity) pannelView.getContext();
    }

    @Override
    public int showDefault() {
        int y = getTranslationY(frameLayout);
        pannelView.setTranslationY(y);
        inputBaffle.setVisibility(STATE_DEFAULT);
        return y;
    }

    @Override
    public void showInput() {
        if(STATE_INPUT == inputBaffle.getVisibility()){
            return;
        }
        int y = getTranslationY(frameLayout);
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
        boolean isChange;
        //首次弹起或者用户自己改变键盘高度
        if(keyboardRect.height()!=frameLayout.getHeight()){
            ViewUtils.initHeight(frameLayout);
            int y = getTranslationY(frameLayout);
            pannelView.setTranslationY(y);
            isChange = true;
        }else{
            isChange = false;
        }
        //有可能是输入框高度发生变化
        if(!isChange){
            int y = getTranslationY(frameLayout);
            if(keyboardRect.height()!=y){
                ViewUtils.initHeight(frameLayout);
                pannelView.setTranslationY(y);
            }
        }
        return isChange;
    }

    private int getTranslationY(FrameLayout frameLayout){
        View parent = activity.findViewById(android.R.id.content);
        int bottom = parent.getBottom();
        int frameTop = frameLayout.getTop();
        int result = bottom-frameTop;
        int kBHeight = KBSharedPreferences.getDefKeyboardHeight(pannelView.getContext());
        if(result>kBHeight){
            result = kBHeight;
        }
        return  result;
    }
}
