package com.littleyellow.keyboardhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.RegisterHelper;

public class MainActivity extends AppCompatActivity {

    boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        final View inputBar = findViewById(R.id.input_bar_ll);
//        view.setPadding(view.getPaddingLeft(),view.getPaddingTop()+getStatusBarHeight(this),view.getPaddingRight(),view.getPaddingBottom());
//        RegisterHelper.compatInput(this,view);

        final View pannel = findViewById(R.id.pannel);
//        RegisterHelper.compatInputPanel(this,inputBar,pannel);
//        final PannelHelpler pannelHelpler = PannelHelpler.newBuilder()
//                .activity(this)
//                .inputBar(inputBar)
//                .pannelView(pannel)
//                .build();
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.toggle();


//                InputMethodHelper.toggleInputMethod(view,show=!show);
            }
        });
        RegisterHelper.compatInputPanel(this,pannelView);

    }
}
