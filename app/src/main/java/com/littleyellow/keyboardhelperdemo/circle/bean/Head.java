package com.littleyellow.keyboardhelperdemo.circle.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleyellow.keyboardhelperdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 小黄 on 2018/10/16.
 */

public class Head implements Item {
    @Override
    public int getLayout() {
        return R.layout.head_circle;
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.big_image)
        ImageView bigImage;
        @BindView(R.id.user_photo_iv)
        ImageView userPhotoIv;
        @BindView(R.id.user_name_tv)
        TextView userNameTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
