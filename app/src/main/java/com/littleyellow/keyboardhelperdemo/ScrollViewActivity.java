package com.littleyellow.keyboardhelperdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.littleyellow.keyboardhelper.RegisterHelper;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_scroll_layout);
        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        final View parent = findViewById(R.id.parent);
        RegisterHelper.compatScrollInput(this, new RegisterHelper.ScrollListener() {
            @Override
            public boolean onScroll(Rect keyboardRect, float y) {
                if(y>0){
                    return true;
                }
                parent.setTranslationY(y);
                return true;
            }

            @Override
            public View getInputBar(View focusView) {
                return null==focusView?null:(View) focusView.getParent();
            }
        });
    }
}
