package com.littleyellow.keyboardhelperdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/9/24 0024.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<Integer> data;

    CommentCallback mCallback;

    public CommentAdapter(List<Integer> data) {
        this.data = data;
    }

    public CommentAdapter(List<Integer> data, CommentCallback callback) {
        this.data = data;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.NameTv.setText("我是"+position);
        holder.contentTv.setText("今天是"+position+"号");
        holder.comment1Tv.setText("我来评论1");
        holder.comment2Tv.setText("我来评论2");

        holder.comment1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick(holder.comment1Tv,holder.comment1Tv.getText().toString());
            }
        });
        holder.comment2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick(holder.comment2Tv,holder.comment2Tv.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{

        TextView NameTv;
        TextView contentTv;
        TextView comment1Tv;
        TextView comment2Tv;

        public ViewHolder(View itemView) {
            super(itemView);
            NameTv = (TextView) itemView.findViewById(R.id.name);
            contentTv = (TextView) itemView.findViewById(R.id.content);
            comment1Tv = (TextView) itemView.findViewById(R.id.comment1);
            comment2Tv = (TextView) itemView.findViewById(R.id.comment2);


        }
    }

    public interface CommentCallback{
        void onClick(View view,String msg);
    }
}
