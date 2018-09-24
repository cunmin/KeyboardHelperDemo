package com.littleyellow.keyboardhelperdemo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.RegisterHelper;

public class MainActivity extends AppCompatActivity {

    boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        final View inputBar = findViewById(R.id.input_bar_ll);
//        final View pannel = findViewById(R.id.pannel);
        final ImageView toggle_iv = (ImageView) findViewById(R.id.toggle_iv);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.toggle();


//                InputMethodHelper.toggleInputMethod(view,show=!show);
            }
        });
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
        });

        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.setVisibility(View.VISIBLE);
                pannelView.showInput();
            }
        });

        findViewById(R.id.ScrollViewActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScrollViewActivity.class));
            }
        });
        findViewById(R.id.CommentListActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CommentListActivity.class));
            }
        });

    }
}
