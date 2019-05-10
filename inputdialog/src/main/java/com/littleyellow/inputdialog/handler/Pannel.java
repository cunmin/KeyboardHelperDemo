package com.littleyellow.inputdialog.handler;

import android.content.Context;

import com.littleyellow.inputdialog.ActionListener;
import com.littleyellow.inputdialog.InputDialog;

import static com.littleyellow.inputdialog.utils.KBUtils.hideSoftInput;

public class Pannel implements InputState {

    @Override
    public void show(Context context, IShowHandler handler) {
        handler.showPannel();
        hideSoftInput(context);
    }

    @Override
    public void setListener(ActionListener listener) {

    }

    @Override
    public void toggle(InputDialog dialog) {
//        view.showInput();
    }
}
