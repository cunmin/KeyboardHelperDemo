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
            final BigImage.ViewHolder viewHolder = (BigImage.ViewHolder) holder;
            BigImage text = (BigImage) data.get(position);
            viewHolder.logo.setImageResource(text.getPhoto());
            viewHolder.name.setText(text.getName());
            viewHolder.content.setText(text.getContent());
            viewHolder.image1.setImageResource(text.getRes());
            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClick(v,viewHolder.content.getText().toString(),position);
                }
            });
        }else if(holder instanceof ThreeImage.ViewHolder){
            final ThreeImage.ViewHolder viewHolder = (ThreeImage.ViewHolder) holder;
            ThreeImage text = (ThreeImage) data.get(position);
            viewHolder.logo.setImageResource(text.getPhoto());
            viewHolder.name.setText(text.getName());
            viewHolder.content.setText(text.getContent());
            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClick(v,viewHolder.content.getText().toString(),position);
                }
            });
        }else if(holder instanceof Text.ViewHolder){
            final Text.ViewHolder viewHolder = (Text.ViewHolder) holder;
            Text text = (Text) data.get(position);
            viewHolder.name.setText(text.getName());
            viewHolder.logo.setImageResource(text.getPhoto());
            viewHolder.content.setText(text.getContent());
            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClick(v,viewHolder.content.getText().toString(),position);
                }
            });
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
