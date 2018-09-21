package com.littleyellow.keyboardhelper;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.littleyellow.keyboardhelper.utils.KBSharedPreferences;
import com.littleyellow.keyboardhelper.utils.ViewUtils;

import static com.littleyellow.keyboardhelper.utils.KBUtils.showSoftInput;

/**
 * Created by 小黄 on 2018/9/19.
 */

public class PannelHelpler {

    Activity activity;

    View inputBar;

    View pannelView;

    float priBottom = 0;

    private PannelHelpler(Builder builder) {
        activity = builder.activity;
        inputBar = builder.inputBar;
        pannelView = builder.pannelView;

        InputMethodHelper.assistActivity(activity, new InputMethodHelper.OnInputMethodListener() {

            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {

                if(show){
                    float priBottom = getPriBottom();
                    inputBar.setTranslationY(keyboardRect.top - priBottom);
//                    panel.setTranslationY(keyboardRect.top);
                }else{
//                    if(panel.getVisibility()==View.VISIBLE){
//
//                    }else{
//                        inputBar.setTranslationY(0);
//                        panel.setTranslationY(0);
//                    }
                    inputBar.setTranslationY(0);
                }
            }
        });
    }

    public void showPannel(){
        int validPanelHeight = KBSharedPreferences.getDefKeyboardHeight(activity);
        View view = activity.findViewById(android.R.id.content);
        int y = view.getBottom()-validPanelHeight;
        float priBottom = getPriBottom();
        inputBar.setTranslationY(y-priBottom);
        ViewUtils.initHeight(pannelView);
        pannelView.setY(y);
//        pannelView.setVisibility(View.VISIBLE);
//        hideSoftInput(activity);
    }

    private float getPriBottom(){
        if(0==priBottom){
            int[] location = new int[2];
            inputBar.getLocationOnScreen(location);
            priBottom= location[1]+inputBar.getHeight();
        }
        return priBottom;
    }

    public boolean isShowPannel(){
        return false;//pannelView.getVisibility()==View.VISIBLE;
    }

    public void showInput(){
        EditText view = forSearch(inputBar);
        if(null!=view){
            showSoftInput(activity,view);
        }
//        pannelView.setVisibility(View.GONE);
//        pannelView.setTranslationY(0);
    }

    private EditText forSearch(View view){
        if(null==view){
            return null;
        }else if(view instanceof EditText){
            return (EditText) view;
        }else{
            if(view instanceof ViewGroup){
                ViewGroup group = (ViewGroup) view;
                int count = group.getChildCount();
                EditText edit = null;
                for(int i=0;i<count;i++){
                    View child = group.getChildAt(i);
                    if(child instanceof EditText){
                        edit = (EditText) child;
                        break;
                    }else if(child instanceof ViewGroup){
                        EditText search = forSearch(child);
                        if(null!=search){
                            edit = search;
                            break;
                        }
                    }
                }
                return edit;
            }else{
                return null;
            }
        }

    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Activity activity;
        private View inputBar;
        private View pannelView;

        private Builder() {
        }

        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder inputBar(View inputBar) {
            this.inputBar = inputBar;
            return this;
        }

        public Builder pannelView(View pannelView) {
            this.pannelView = pannelView;
            return this;
        }

        public PannelHelpler build() {
            return new PannelHelpler(this);
        }
    }
}
