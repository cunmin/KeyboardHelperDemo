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
import com.littleyellow.keyboardhelper.utils.KBSharedPreferences;
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

    private View inputBarView;

    private int parentBottom;

    private final IShowHandler showHandler;

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
        Activity activity = (Activity) context;
        showHandler = StatusBarUtil.isFullScreen(activity)?
                      new FSShowHandler(frameLayout,inputBaffle):
                      new NFSShowHandler(this,inputBaffle);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        if(getChildCount()!=2){
            throw new IllegalArgumentException("输入条为第一个子控件，选择板为第二个子控件");
        }
        inputBarView = getChildAt(0);
        View view = getChildAt(1);
        removeView(view);
        addView(frameLayout,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayout.addView(view);
        inputBaffle.setBackgroundColor(Color.WHITE);
        frameLayout.addView(inputBaffle, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        ViewUtils.initHeight(frameLayout);
        final Activity activity = (Activity) getContext();
        post(new Runnable() {
            @Override
            public void run() {
                View parent = activity.findViewById(android.R.id.content);
                parentBottom = parent.getBottom();
            }
        });
        InputMethodHelper.assistActivity(activity, new InputMethodHelper.OnInputMethodListener() {

            @Override
            public void onInputMethodStatusChanged(Rect keyboardRect, boolean show) {
                EditText view = getEditText();
                View curnView = activity.getCurrentFocus();
                if(view!=curnView){
                    checkHeight(keyboardRect);
                    showDefault();
                    return;
                }
                if(show){
                    checkHeight(keyboardRect);
//                    setTranslationY(0);
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
        });
    }

    private void checkHeight(Rect keyboardRect){
        if(showHandler.checkHeight(keyboardRect,frameLayout)&&null!=listener){
            listener.onHeightChange(keyboardRect.height());
        }
    }

    private void setDefalutState(){
        showHandler.showDefault();
    }

    private void setInputState(Rect keyboardRect){
        showHandler.showInput();
    }

    private void setPannelState(){
        showHandler.showPannel();
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
        int validPanelHeight = KBSharedPreferences.getDefKeyboardHeight(getContext());
//        setTranslationY(validPanelHeight);
        setDefalutState();
        if(null!=listener){
            listener.onShowDefault(validPanelHeight);
        }
    }

    public void toggle(){
        int bottom = inputBarView.getBottom();
        final Activity activity = (Activity) getContext();
        View parent = activity.findViewById(android.R.id.content);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int curBottom= location[1]+parent.getHeight();
        int top = parent.getTop();
        int bottom2 = parent.getBottom();
        if(isShowDefault()){
//            setTranslationY(0);
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
}
