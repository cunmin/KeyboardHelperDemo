package com.littleyellow.inputdialog.handler;

import android.content.Context;

import com.littleyellow.inputdialog.ActionListener;
import com.littleyellow.inputdialog.InputDialog;

public interface InputState {

    void show(Context context, IShowHandler handler);

    void setListener(ActionListener listener);

    void toggle(InputDialog dialog);
}
