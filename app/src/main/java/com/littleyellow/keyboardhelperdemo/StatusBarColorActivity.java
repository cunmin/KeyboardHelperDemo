package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.RegisterHelper;
import com.littleyellow.keyboardhelper.statusbar.StatusBarColor;

import static com.littleyellow.keyboardhelper.PannelView.setInputMode;

/**
 * Created by 小黄 on 2018/10/9.
 */

public class StatusBarColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_color);
        StatusBarColor.setColor(this, Color.RED);
        setInputMode(this);
        final ImageView toggle_iv = (ImageView) findViewById(R.id.toggle_iv);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.toggle();
            }
        });
        final EditText inputEt = (EditText) findViewById(R.id.input_et);
        RegisterHelper.compatInputPanel(this,pannelView);
        pannelView.setListener(new ActionListener() {
            @Override
            public void onShowDefault(int pannelHeight) {
                Log.e("PannelView","onShowDefault"+pannelHeight);
                toggle_iv.setImageResource(R.mipmap.icon_face);
//                pannelView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShowInput(Rect keyboardRect, int keyboardHeight) {
                Log.e("PannelView","onShowInput"+keyboardHeight);
                toggle_iv.setImageResource(R.mipmap.icon_face);
            }

            @Override
            public void onShowPannel(int pannelHeight) {
                Log.e("PannelView","onShowPannel"+pannelHeight);
                toggle_iv.setImageResource(R.mipmap.icon_keyboard);
            }

            @Override
            public void onHeightChange(int changeHeight) {
                Log.e("PannelView","onHeightChange"+changeHeight);
            }

            @Override
            public EditText actionEditText() {
                return inputEt;
            }
        });
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }
}
