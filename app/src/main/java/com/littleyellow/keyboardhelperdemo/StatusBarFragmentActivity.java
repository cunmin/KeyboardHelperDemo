package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.littleyellow.keyboardhelper.statusbar.StatusBarView;
import com.littleyellow.keyboardhelperdemo.util.TabFragmentHelper;

import static com.littleyellow.keyboardhelper.PannelView.setInputMode;

/**
 * Created by 小黄 on 2018/10/9.
 */

public class StatusBarFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_status_bar_fragment);
        setInputMode(this);
//        StatusBarColor.setColor(this, Color.RED);

        StatusBarView.setColor(this, Color.parseColor("#ffffff"));
        TabLayout tableLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabFragmentHelper.newBuilder()
                .mTabLayout(tableLayout)
                .mMainViewPager(viewPager)
                .fm(getSupportFragmentManager())
                .addTab("0",StatusBarFragment.class,StatusBarFragment.getBundle(0))
                .addTab("1",StatusBarFragment.class,StatusBarFragment.getBundle(1))
                .isPost(true)
                .build()
                .setupViewPager();
    }
}
