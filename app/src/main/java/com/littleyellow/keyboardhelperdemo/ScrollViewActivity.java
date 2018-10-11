package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleyellow.keyboardhelper.RegisterHelper;
import com.littleyellow.keyboardhelper.statusbar.StatusBarColor;

import static com.littleyellow.keyboardhelper.PannelView.setInputMode;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_layout);
        setInputMode(this);
//        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        final View parent = findViewById(R.id.parent);
        RegisterHelper.compatScrollInput(this, new RegisterHelper.ScrollListener() {
            @Override
            public boolean onScroll(Rect keyboardRect, float y) {
//                if(y>0){
//                    return true;
//                }
                parent.setTranslationY(y);
                return true;
            }

            @Override
            public View getInputBar(View focusView) {
                return null==focusView?null:(View) focusView.getParent();
            }
        });

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        final LinearLayout titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        int stausBarHeight = StatusBarColor.setColor(this, Color.parseColor("#FF0008"));
//        if(null!=stausBar){
//            titleLayout.addView(stausBar,0);
//            titleLayout.setBackgroundColor(Color.parseColor("#FF0008"));
//        }
        titleLayout.setPadding(0,stausBarHeight,0,0);
        final TextView titleText = (TextView) findViewById(R.id.title);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                final float percent = (scrollY * 1f) / v.getMaxScrollAmount();
                titleLayout.setAlpha(percent);

                titleText.setTextColor(percent<0.3?Color.parseColor("#000000"):Color.WHITE);

//                StatusBarView.updateStateBar(ScrollViewActivity.this,Color.parseColor("#FF0008"),percent<0.3?DARK_FONT_ON:DARK_FONT_OFF);
                StatusBarColor.setColor(ScrollViewActivity.this, percent<0.3?Color.parseColor("#FF0008"):Color.BLUE);
            }
        });
    }
}
