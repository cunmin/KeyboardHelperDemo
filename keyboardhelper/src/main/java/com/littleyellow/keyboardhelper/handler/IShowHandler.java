package com.littleyellow.keyboardhelper.handler;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 小黄 on 2018/9/28.
 */

public interface IShowHandler {

    int STATE_DEFAULT = View.INVISIBLE;

    int STATE_INPUT = View.VISIBLE;

    int STATE_PANNEL = View.GONE;

    void showDefault();

    void showInput();

    void showPannel();

    boolean checkHeight(Rect keyboardRect,FrameLayout frameLayout);
}
