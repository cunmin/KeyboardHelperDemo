package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.statusbar.StatusBarView;
import com.littleyellow.keyboardhelperdemo.circle.bean.BigImage;
import com.littleyellow.keyboardhelperdemo.circle.bean.CommentAdapter;
import com.littleyellow.keyboardhelperdemo.circle.bean.Head;
import com.littleyellow.keyboardhelperdemo.circle.bean.Item;
import com.littleyellow.keyboardhelperdemo.circle.bean.Text;
import com.littleyellow.keyboardhelperdemo.circle.bean.ThreeImage;
import com.littleyellow.keyboardhelperdemo.common.SimpleCommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static com.littleyellow.keyboardhelper.PannelView.setInputMode;
import static com.littleyellow.keyboardhelper.utils.KBUtils.hideSoftInput;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class CommentListActivity extends AppCompatActivity implements CommentAdapter.CommentCallback, EmoticonsFuncView.OnEmoticonsPageViewListener,EmoticonsToolBarView.OnToolBarItemClickListener {

    private RecyclerView recyclerview;

    private PannelView pannelView;

    private EditText input_et;

    private View focusView;

    private int position;

    private LinearLayoutManager layoutManager;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;

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

        findViewById(R.id.btn_voice_or_text).setVisibility(View.GONE);
        final ImageView btn_face = (ImageView) findViewById(R.id.btn_face);
        final LinearLayout faceLl = (LinearLayout) findViewById(R.id.face_ll);
        init();

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        pannelView.setVisibility(View.INVISIBLE);
        input_et = (EditText) findViewById(R.id.input_et);
        final Button btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setVisibility(View.VISIBLE);
        btnSend.setEnabled(false);
        input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSend.setEnabled(TextUtils.isEmpty(s)?false:true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        toggle_iv.setVisibility(View.GONE);
        pannelView = (PannelView) findViewById(R.id.pannel_view);
        final EditText inputEt = (EditText) findViewById(R.id.input_et);
        btn_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pannelView.isShowPannel()) {
                    pannelView.toggle();
                }else if(View.GONE==faceLl.getVisibility()){
                    pannelView.toggle();
                }
                faceLl.setVisibility(View.GONE);
            }
        });

        toggle_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pannelView.isShowPannel()) {
                    pannelView.toggle();
                }else if(View.VISIBLE==faceLl.getVisibility()){
                    pannelView.toggle();
                }
                faceLl.setVisibility(View.VISIBLE);
            }
        });
        pannelView.setListener(new ActionListener() {
            @Override
            public void onShowDefault(int pannelHeight) {
                Log.e("PannelView","onShowDefault"+pannelHeight);
                btn_face.setImageResource(R.drawable.icon_face_nomal);
            }

            @Override
            public void onShowInput(Rect keyboardRect, int keyboardHeight) {
                Log.e("PannelView","onShowInput"+keyboardHeight);
                btn_face.setImageResource(R.drawable.icon_face_nomal);
                if(null!=focusView) {
                    int y = pannelView.getOffset(focusView);
                    recyclerview.scrollBy(0,-y);
                    focusView = null;
                }
            }

            @Override
            public void onShowPannel(int pannelHeight) {
                Log.e("PannelView","onShowPannel"+pannelHeight);
                if(View.VISIBLE==faceLl.getVisibility()){
                    btn_face.setImageResource(R.drawable.icon_face_nomal);
                }else{
                    btn_face.setImageResource(R.drawable.icon_softkeyboard_nomal);
                }
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
    }



    @Override
    public void onClick(View view,String msg,int positon) {
        focusView = view;
        position = positon;
        CharSequence hints = input_et.getHint();
        if(!("评论:"+msg).equals(hints)){
            input_et.setHint("评论:"+msg);
            input_et.setText(null);
        }
        pannelView.setVisibility(View.VISIBLE);
        pannelView.showInput();
    }

    public Item getItem(int postition){
        int index = new Random().nextInt(3);
        if(0==index){
            int index2 = new Random().nextInt(5);
            Text item = new Text();
            if(0==index2){
                item.setName("路飞");
                item.setContent("我是要成为海贼王的男人。");
                item.setPhoto(R.mipmap.circle_user_photo);
            }else if(1==index2){
                item.setName("罗宾");
                item.setPhoto(R.mipmap.circle_user_photo1);
                item.setContent("如果真的能让我许下一个愿望的话……我想要……我想要活下去！把我也一起带到海上吧！");
            }else if(2==index2){
                item.setName("乌索普");
                item.setPhoto(R.mipmap.circle_user_photo2);
                item.setContent("我以我父亲是海贼为荣！我以他是勇敢的海上战士为荣！你说的没错！我喜欢吹牛…但是，我夸耀我拥有的海贼血统！我不需要伪装！我是海贼的儿子！");
            }else if(3==index2){
                item.setName("山治");
                item.setPhoto(R.mipmap.circle_user_photo3);
                item.setContent("总有一天…我一定会找到“ALLBLUE”传说之海的！");
            }else if(4==index2){
                item.setName("索隆");
                item.setPhoto(R.mipmap.circle_user_photo4);
                item.setContent("团队精神到底是什么？互相帮助，互相袒护就算是吗？也有人这么认为吧。我是认为那根本只是唬人！应该是每个人抱着必死决心做自己的事，“我做好自己的部分”“接下来轮到你”“做不好的话我就揍扁你”要是有这种决心才能算是团队精神吧！");
            }
            return item;
        }else if(1==index){
            BigImage item = new BigImage();
            int index2 = new Random().nextInt(6);
            if(0==index2){
                item.setName("路飞");
                item.setContent("你 说 什 么 ？");
                item.setPhoto(R.mipmap.circle_user_photo);
                item.setRes(R.mipmap.image5);
            }else if(1==index2){
                item.setName("罗宾");
                item.setPhoto(R.mipmap.circle_user_photo1);
                item.setRes(R.mipmap.image1);
                item.setContent("乔巴好可爱呀!");
            }else if(2==index2){
                item.setName("乌索普");
                item.setPhoto(R.mipmap.circle_user_photo2);
                item.setContent("我以我父亲是海贼为荣！我以他是勇敢的海上战士为荣！你说的没错！我喜欢吹牛…但是，我夸耀我拥有的海贼血统！我不需要伪装！我是海贼的儿子！");
            }else if(3==index2){
                item.setName("山治");
                item.setPhoto(R.mipmap.circle_user_photo3);
                item.setRes(R.mipmap.image3);
                item.setContent("我们好帅！");
            }else if(4==index2){
                item.setName("路飞");
                item.setPhoto(R.mipmap.circle_user_photo);
                item.setRes(R.mipmap.image6);
                item.setContent("哈哈哈哈~");
            }else if(5==index2){
                item.setName("索隆");
                item.setRes(R.mipmap.image4);
                item.setPhoto(R.mipmap.circle_user_photo4);
                item.setContent("酒呢！！！！！！");
            }

            return item;
        }else{
            ThreeImage item = new ThreeImage();
            item.setName("索隆");
            item.setPhoto(R.mipmap.circle_user_photo4);
            item.setContent("我如果这样就死了，就说明我只是这样程度的人而已！");
            return item;
        }
    }


    public void init() {
        mEmoticonsFuncView = (EmoticonsFuncView) findViewById(R.id.view_epv);
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) findViewById(com.keyboard.view.R.id.view_eiv));
        mEmoticonsToolBarView = ((EmoticonsToolBarView) findViewById(com.keyboard.view.R.id.view_etv));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        PageSetAdapter pageSetAdapter = SimpleCommonUtils.getCommonAdapter(this, null);
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

}
