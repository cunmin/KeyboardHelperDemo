package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.statusbar.StatusBarView;

import java.util.ArrayList;
import java.util.List;

import static com.littleyellow.keyboardhelper.utils.ViewUtils.getScreenHeight;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class CommentListActivity extends AppCompatActivity implements CommentAdapter.CommentCallback {

    private RecyclerView recyclerview;

    private PannelView pannelView;

    private EditText input_et;

    private View focusView;

    private int position;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list_layout);
        View stausBar = StatusBarView.setColor(this, Color.parseColor("#9A7750"));
        if(null!=stausBar){
            stausBar.setBackgroundResource(R.drawable.bg_title_gradient);
        }
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        input_et = (EditText) findViewById(R.id.input_et);
        List<Integer> data = new ArrayList<>();
        for (int i=1;i<50;i++){
            data.add(i);
        }
        CommentAdapter adapter = new CommentAdapter(data,this);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        int screenHeight = getScreenHeight(this);
//        recyclerview.setPadding(0,screenHeight,0,0);
//        recyclerview.setClipChildren(false);
        pannelView.boundRecylerview(this,recyclerview.getLayoutParams());
        final ImageView toggle_iv = (ImageView) findViewById(R.id.toggle_iv);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        final EditText inputEt = (EditText) findViewById(R.id.input_et);
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
//                recyclerview.setClipChildren(true);
                recyclerview.setTranslationY(0);
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
//                    recyclerview.setClipChildren(false);
                    recyclerview.setTranslationY(y);
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

            @Override
            public EditText actionEditText() {
                return inputEt;
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
    public void onClick(View view,String msg,int positon) {
        focusView = view;
        position = positon;
        String hint = input_et.getHint().toString();
        if(!hint.equals("评论:"+msg)){
            input_et.setHint("评论:"+msg);
            input_et.setText(null);
        }
        pannelView.setVisibility(View.VISIBLE);
        pannelView.showInput();
    }


}
