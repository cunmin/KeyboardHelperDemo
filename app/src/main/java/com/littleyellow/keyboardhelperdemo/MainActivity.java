package com.littleyellow.keyboardhelperdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.RegisterHelper;
import com.littleyellow.keyboardhelper.statusbar.StatusBarView;
import com.squareup.leakcanary.LeakCanary;

import static com.littleyellow.keyboardhelper.PannelView.setInputMode;

public class MainActivity extends AppCompatActivity {

    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeakCanary.install(getApplication());
        setInputMode(this);
        setContentView(R.layout.activity_main);
        StatusBarView.setColor(this, Color.parseColor("#ffffff"));
//        StatusBarColor.setColor(this, Color.RED);

//        final View pannel = findViewById(R.id.pannel);
        final ImageView toggle_iv = (ImageView) findViewById(R.id.toggle_iv);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.toggle();
            }
        });
        final EditText inputEt = (EditText) findViewById(R.id.input_et);
        final EditText edittext = (EditText) findViewById(R.id.edittext);
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

        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.setVisibility(View.VISIBLE);
                pannelView.showInput();
            }
        });

        findViewById(R.id.StatusBarViewActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StatusBarViewActivity.class));
            }
        });

        findViewById(R.id.StatusBarColorActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,StatusBarColorActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.StatusBarFragmentActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StatusBarFragmentActivity.class));
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
