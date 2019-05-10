package com.littleyellow.inputdialog.handler;

import android.content.Context;

import com.littleyellow.inputdialog.ActionListener;
import com.littleyellow.inputdialog.InputDialog;

import static com.littleyellow.inputdialog.utils.KBUtils.hideSoftInput;

public class NoState implements InputState {

    private ActionListener listener;

    @Override
    public void show(Context context, IShowHandler handler) {
        hideSoftInput(context);
        int validPanelHeight = handler.showDefault();
        if(null != listener){
            listener.onShowDefault(validPanelHeight);
        }
    }

    @Override
    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void toggle(InputDialog dialog) {
//        view.showPannel(false);
    }
}
