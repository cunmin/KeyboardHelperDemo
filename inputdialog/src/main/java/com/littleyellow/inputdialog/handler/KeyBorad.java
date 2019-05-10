package com.littleyellow.inputdialog.handler;

import android.content.Context;
import android.widget.EditText;

import com.littleyellow.inputdialog.ActionListener;
import com.littleyellow.inputdialog.InputDialog;

import static com.littleyellow.inputdialog.utils.KBUtils.showSoftInput;

public class KeyBorad implements InputState {

    public final InputDialog dialog;

    private EditText editText;

    public KeyBorad(InputDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void show(Context context, IShowHandler handler) {
        EditText view = dialog.getEditText();
        handler.showInput();
        if(null!=view){
            showSoftInput(context,view);
        }
    }

    @Override
    public void setListener(ActionListener listener) {

    }

    @Override
    public void toggle(InputDialog dialog) {
//        view.showPannel(true);
    }
}
