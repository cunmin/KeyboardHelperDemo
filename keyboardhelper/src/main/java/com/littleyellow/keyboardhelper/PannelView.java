package com.littleyellow.keyboardhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.littleyellow.keyboardhelper.handler.FSShowHandler;
import com.littleyellow.keyboardhelper.handler.IShowHandler;
import com.littleyellow.keyboardhelper.handler.NFSShowHandler;
import com.littleyellow.keyboardhelper.statusbar.StatusBarUtil;
import com.littleyellow.keyboardhelper.utils.KeyboardListener;
import com.littleyellow.keyboardhelper.utils.ViewUtils;

import static com.littleyellow.keyboardhelper.handler.IShowHandler.STATE_DEFAULT;
import static com.littleyellow.keyboardhelper.handler.IShowHandler.STATE_INPUT;
import static com.littleyellow.keyboardhelper.handler.IShowHandler.STATE_PANNEL;
import static com.littleyellow.keyboardhelper.utils.KBUtils.hideSoftInput;
import static com.littleyellow.keyboardhelper.utils.KBUtils.showSoftInput;

/**
 * Created by 小黄 on 2018/9/20.
 */

public class PannelView extends LinearLayout{

    private final FrameLayout frameLayout;

    private final View inputBaffle;

    private boolean interruptDown = false;

    private ActionListener listener;

    private EditText editText;

    private IShowHandler showHandler;

    public PannelView(Context context) {
        this(context, null);
    }

    public PannelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        frameLayout  = new FrameLayout(context);
        inputBaffle = new View(context);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        if(getChildCount()!=2){
            throw new IllegalArgumentException("输入条为第一个子控件，选择板为第二个子控件");
        }
        View view = getChildAt(1);
        removeView(view);
        addView(frameLayout,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.addView(view);
        inputBaffle.setBackgroundColor(Color.WHITE);
        frameLayout.addView(inputBaffle, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        ViewUtils.initHeight(frameLayout);
        registerListener();
    }

    private void registerListener(){
        final Activity activity = (Activity) getContext();
        KeyboardHelper.newBuilder()
                .host(activity)
                .onInputMethodListener(new KeyboardListener.OnInputMethodListener() {
                    @Override
                    public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                        onInputStatusChanged(activity,keyboardRect,show);
                    }
                })
                .build();
    }

    private void onInputStatusChanged(Activity activity,Rect keyboardRect, boolean show){
        EditText view = getEditText();
        View curnView = activity.getCurrentFocus();
        if(view!=curnView&&!isShowPannel()){
            checkHeight(keyboardRect);
            showDefault();
            return;
        }
        if(show){
            checkHeight(keyboardRect);
            setInputState(keyboardRect);
            if(null!=listener){
                listener.onShowInput(keyboardRect,keyboardRect.height());
            }
        }else{
            if(!isShowPannel()&&!interruptDown){
                showDefault();

            }else{
                setPannelState();
                if(null!=listener){
                    listener.onShowPannel(frameLayout.getHeight());
                }
            }
        }
        interruptDown = false;
    }

    private void checkHeight(Rect keyboardRect){
        if(getShowHandler().checkHeight(keyboardRect,frameLayout)&&null!=listener){
            listener.onHeightChange(keyboardRect.height());
        }
    }

    private int setDefalutState(){
        return getShowHandler().showDefault();
    }

    private void setInputState(Rect keyboardRect){
        getShowHandler().showInput();
    }

    private void setPannelState(){
        getShowHandler().showPannel();
    }

    private EditText getEditText(){
        if(null==editText){
            if(null==listener){
                editText = forSearch(getChildAt(0));
            }else{
                editText = listener.actionEditText();
            }
        }
        return editText;
    }

    private EditText forSearch(View view){
        if(null==view){
            return null;
        }else if(view instanceof EditText){
            return (EditText) view;
        }else{
            if(view instanceof ViewGroup){
                ViewGroup group = (ViewGroup) view;
                int count = group.getChildCount();
                EditText edit = null;
                for(int i=0;i<count;i++){
                    View child = group.getChildAt(i);
                    if(child instanceof EditText){
                        edit = (EditText) child;
                        break;
                    }else if(child instanceof ViewGroup){
                        EditText search = forSearch(child);
                        if(null!=search){
                            edit = search;
                            break;
                        }
                    }
                }
                return edit;
            }else{
                return null;
            }
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!isShowDefault()) {
                showDefault();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public boolean isShowDefault(){
        return STATE_DEFAULT == inputBaffle.getVisibility();
    }

    public boolean isShowInput(){
        return STATE_INPUT == inputBaffle.getVisibility();
    }

    public boolean isShowPannel(){
        return STATE_PANNEL == inputBaffle.getVisibility();
    }

    public int getShowState(){
        return inputBaffle.getVisibility();
    }

    public void showPannel(){
        interruptDown = true;
        hideSoftInput(getContext());
    }

    public void showInput(){
        EditText view = getEditText();
        if(null!=view){
            showSoftInput(getContext(),view);
        }
    }

    public void showDefault(){
        int validPanelHeight = setDefalutState();
        if(null!=listener){
            listener.onShowDefault(validPanelHeight);
        }
    }

    public void toggle(){
        if(isShowDefault()){
            setPannelState();
            if(null!=listener){
                listener.onShowPannel(frameLayout.getHeight());
            }
        }else if(isShowInput()){
            showPannel();
        }else {
            showInput();
        }
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public IShowHandler getShowHandler() {
        if(null==showHandler) {
            Activity activity = (Activity) frameLayout.getContext();
            showHandler = StatusBarUtil.isFullScreen(activity) ?
                    new FSShowHandler(frameLayout, inputBaffle) :
                    new NFSShowHandler(this,frameLayout,inputBaffle);
        }
        return showHandler;
    }
}
