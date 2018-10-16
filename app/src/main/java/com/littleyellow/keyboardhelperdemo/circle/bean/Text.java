package com.littleyellow.keyboardhelperdemo.circle.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleyellow.keyboardhelperdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 小黄 on 2018/10/16.
 */

public class Text implements Item {

    private String photo;
    private String name;
    private String content;

    @Override
    public int getLayout() {
        return R.layout.item_circle_text;
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder {
        @BindView(R.id.logo)
        ImageView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.replay_containner)
        LinearLayout replayContainner;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
