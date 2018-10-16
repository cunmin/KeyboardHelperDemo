package com.littleyellow.keyboardhelperdemo.circle.bean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littleyellow.keyboardhelperdemo.R;

import java.util.List;

import static com.littleyellow.keyboardhelper.utils.ViewUtils.getScreenHeight;

/**
 * Created by Administrator on 2018/9/24 0024.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> data;

    CommentCallback mCallback;

    public CommentAdapter(List<Item> data) {
        this.data = data;
    }

    public CommentAdapter(List<Item> data, CommentCallback callback) {
        this.data = data;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        if(R.layout.item_circle_big_image == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_big_image,null,false);
            holder = new BigImage.ViewHolder(view);
        }else if(R.layout.item_circle_three_image == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_three_image,null,false);
            holder = new ThreeImage.ViewHolder(view);
        }else if(R.layout.item_circle_text == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_text,null,false);
            holder = new Text.ViewHolder(view);
        }else if(R.layout.head_circle == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_circle,null,false);
            holder = new Head.ViewHolder(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getLayout();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(0==position){
            int screenHeight = getScreenHeight(holder.itemView.getContext());
            holder.itemView.setPadding(0,screenHeight,0,0);
        }else if(data.size()-1==position){
            int screenHeight = getScreenHeight(holder.itemView.getContext());
            holder.itemView.setPadding(0,0,0,screenHeight);
        }else{
            holder.itemView.setPadding(0,0,0,0);
        }

        if(holder instanceof BigImage.ViewHolder){
            ((BigImage.ViewHolder) holder).logo.setImageResource(R.mipmap.circle_user_photo);
        }else if(holder instanceof ThreeImage.ViewHolder){
            ((ThreeImage.ViewHolder) holder).logo.setImageResource(R.mipmap.circle_user_photo);
        }else if(holder instanceof Text.ViewHolder){
            ((Text.ViewHolder) holder).logo.setImageResource(R.mipmap.circle_user_photo);
        }else if(holder instanceof Head.ViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface CommentCallback{
        void onClick(View view,String msg,int positon);
    }
}
