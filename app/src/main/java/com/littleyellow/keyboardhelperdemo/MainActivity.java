package com.littleyellow.keyboardhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.littleyellow.keyboardhelper.RegisterHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        View view = findViewById(R.id.input_bar_ll);
        RegisterHelper.compatInput(this,view);
    }
}
