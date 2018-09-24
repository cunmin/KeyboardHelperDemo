package com.littleyellow.keyboardhelperdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class CommentListActivity extends AppCompatActivity implements CommentAdapter.CommentCallback {

    private RecyclerView recyclerview;

    private PannelView pannelView;

    private EditText input_et;

    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_comment_list_layout);
        StatusBarUtil.setColor(this,0,R.drawable.bg_title_gradient);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        input_et = (EditText) findViewById(R.id.input_et);
        List<Integer> data = new ArrayList<>();
        for (int i=1;i<50;i++){
            data.add(i);
        }
        CommentAdapter adapter = new CommentAdapter(data,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);

        final ImageView toggle_iv = (ImageView) findViewById(R.id.toggle_iv);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pannelView.toggle();
            }
        });
        pannelView.setListener(new ActionListener() {
            @Override
            public void onShowDefault(int pannelHeight) {
                Log.e("PannelView","onShowDefault"+pannelHeight);
                toggle_iv.setImageResource(R.mipmap.icon_face);
                pannelView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShowInput(Rect keyboardRect, int keyboardHeight) {
                Log.e("PannelView","onShowInput"+keyboardHeight);
                toggle_iv.setImageResource(R.mipmap.icon_face);

                if(null!=focusView) {
                    int[] location = new int[2];
                    focusView.getLocationOnScreen(location);
                    int priBottom = location[1] + focusView.getHeight();
                    int top = pannelView.getTop();
                    int y =  top- priBottom;
                    recyclerview.scrollBy(0, y);
                    focusView = null;
                }
            }

            @Override
            public void onShowPannel(int pannelHeight) {
                Log.e("PannelView","onShowPannel"+pannelHeight);
                toggle_iv.setImageResource(R.mipmap.icon_keyboard);
            }

            @Override
            public void onHeightChange(int changeHeight) {
                Log.e("PannelView","onHeightChange"+changeHeight);
            }
        });

//        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pannelView.setVisibility(View.VISIBLE);
//                pannelView.showInput();
//            }
//        });
    }

    @Override
    public void onClick(View view,String msg) {
        focusView = view;
        String hint = input_et.getHint().toString();
        if(!hint.equals("评论:"+msg)){
            input_et.setHint("评论:"+msg);
            input_et.setText(null);
        }
        pannelView.setVisibility(View.VISIBLE);
        pannelView.showInput();
    }


}
