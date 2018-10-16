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

public class ThreeImage extends Text {
    @Override
    public int getLayout() {
        return R.layout.item_circle_three_image;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        @BindView(R.id.logo)
        ImageView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.image1)
        ImageView image1;
        @BindView(R.id.image2)
        ImageView image2;
        @BindView(R.id.image3)
        ImageView image3;
        @BindView(R.id.img_containner)
        LinearLayout imgContainner;
        @BindView(R.id.replay_containner)
        LinearLayout replayContainner;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
