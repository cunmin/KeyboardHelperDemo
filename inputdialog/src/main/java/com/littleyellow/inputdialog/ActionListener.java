package com.littleyellow.inputdialog;

import android.graphics.Rect;
import android.widget.EditText;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public interface ActionListener {

    void onShowDefault(int pannelHeight);

    void onShowInput(Rect keyboardRect, int keyboardHeight);

    void onShowPannel(int pannelHeight);

    void onHeightChange(int changeHeight);

    EditText actionEditText();
}
