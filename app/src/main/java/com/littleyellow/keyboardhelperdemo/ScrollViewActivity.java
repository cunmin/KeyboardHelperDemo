package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.statusbar.StatusBarColor;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class ScrollViewActivity extends AppCompatActivity {

    private PannelView pannelView;

    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_layout);
        StatusBarColor.setColor(this,Color.RED);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        final LinearLayout titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        int stausBarHeight = StatusBarColor.setColor(this, Color.parseColor("#FF0008"));
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
