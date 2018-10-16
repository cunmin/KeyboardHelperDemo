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
import com.littleyellow.keyboardhelperdemo.circle.bean.BigImage;
import com.littleyellow.keyboardhelperdemo.circle.bean.CommentAdapter;
import com.littleyellow.keyboardhelperdemo.circle.bean.Head;
import com.littleyellow.keyboardhelperdemo.circle.bean.Item;
import com.littleyellow.keyboardhelperdemo.circle.bean.Text;
import com.littleyellow.keyboardhelperdemo.circle.bean.ThreeImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static com.littleyellow.keyboardhelper.PannelView.setInputMode;
import static com.littleyellow.keyboardhelper.utils.KBUtils.hideSoftInput;

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
        setInputMode(this);
        View stausBar = StatusBarView.setColor(this, Color.parseColor("#9A7750"));
        if(null!=stausBar){
            stausBar.setBackgroundResource(R.drawable.bg_title_gradient);
        }
//        StatusBarColor.setColor(this, Color.RED);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        pannelView.setVisibility(View.INVISIBLE);
        input_et = (EditText) findViewById(R.id.input_et);
        List<Item> data = new ArrayList<>();
        data.add(new Head());
        for (int i=1;i<50;i++){
            data.add(getItem(i));
        }
        CommentAdapter adapter = new CommentAdapter(data,this);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==SCROLL_STATE_DRAGGING ){
                    pannelView.setVisibility(View.INVISIBLE);
                    hideSoftInput(CommentListActivity.this);
                }
            }
        });
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
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
            }

            @Override
            public void onShowInput(Rect keyboardRect, int keyboardHeight) {
                Log.e("PannelView","onShowInput"+keyboardHeight);
                toggle_iv.setImageResource(R.mipmap.icon_face);
                if(null!=focusView) {
                    int y = pannelView.getOffset(focusView);
                    recyclerview.smoothScrollBy(0,-y);
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

    public Item getItem(int postition){
        Item item;
        int index = new Random().nextInt(3);
        if(0==index){
            item = new Text();
        }else if(1==index){
            item = new BigImage();
        }else{
            item = new ThreeImage();
        }
        return item;
    }


}
