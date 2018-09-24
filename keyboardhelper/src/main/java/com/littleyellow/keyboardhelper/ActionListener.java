package com.littleyellow.keyboardhelper;

import android.graphics.Rect;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public interface ActionListener {

    void onShowDefault(int pannelHeight);

    void onShowInput(Rect keyboardRect,int keyboardHeight);

    void onShowPannel(int pannelHeight);

    void onHeightChange(int changeHeight);

}