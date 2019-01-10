package com.littleyellow.keyboardhelperdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littleyellow.keyboardhelper.ActionListener;
import com.littleyellow.keyboardhelper.PannelView;
import com.littleyellow.keyboardhelper.statusbar.StatusBarView;
import com.littleyellow.keyboardhelperdemo.common.SimpleCommonUtils;

import java.util.ArrayList;

import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;

import static com.littleyellow.keyboardhelper.PannelView.setInputMode;

/**
 * Created by 小黄 on 2018/10/9.
 */

public class StatusBarViewActivity extends AppCompatActivity implements EmoticonsFuncView.OnEmoticonsPageViewListener,EmoticonsToolBarView.OnToolBarItemClickListener {


    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_view);
        StatusBarView.setColor(this, Color.parseColor("#ffffff"));
        setInputMode(this);
        final ImageView btn_face = (ImageView) findViewById(R.id.btn_face);
        final PannelView pannelView = (PannelView) findViewById(R.id.pannel_view);
        final LinearLayout faceLl = (LinearLayout) findViewById(R.id.face_ll);
        init();

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
        findViewById(R.id.toggle_iv).setOnClickListener(new View.OnClickListener() {
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
        final EditText inputEt = (EditText) findViewById(R.id.input_et);

        pannelView.setListener(new ActionListener() {
            @Override
            public void onShowDefault(int pannelHeight) {
                Log.e("PannelView","onShowDefault"+pannelHeight);
//                toggle_iv.setImageResource(R.mipmap.icon_face);
//                pannelView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShowInput(Rect keyboardRect, int keyboardHeight) {
                Log.e("PannelView","onShowInput"+keyboardHeight);
                btn_face.setImageResource(R.drawable.icon_face_nomal);
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
