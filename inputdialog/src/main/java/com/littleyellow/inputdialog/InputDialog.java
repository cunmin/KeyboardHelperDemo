package com.littleyellow.inputdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.littleyellow.inputdialog.handler.InputState;
import com.littleyellow.inputdialog.handler.NoState;

public class InputDialog extends DialogFragment {

    View.OnClickListener clickListener;

    private ActionListener listener;

    public InputDialog() {
        inputState = new NoState();
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    EditText editText;

    public static InputDialog newInstance(String tittle,int layoutId,int toggleId) {
        InputDialog fragment = new InputDialog();

        Bundle bundle = new Bundle();
        bundle.putString("tittle", tittle);
        bundle.putInt("toggleId", toggleId);

        bundle.putInt("layoutId",layoutId);
        fragment.setArguments(bundle);
//        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getArguments().getInt("layoutId");
        View view = inflater.inflate(layoutId, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int toggleId = getArguments().getInt("toggleId");
        View view1 = view.findViewById(toggleId);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=clickListener){
                    inputState.toggle(InputDialog.this);
                    clickListener.onClick(v);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
//        getDialog().getWindow().setGravity(Gravity.BOTTOM);
//        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                showSoftInput(getContext(),editText);
//            }
//        });
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        dialog.seton
        return dialog;
    }

    public EditText getEditText(){
        if(null==editText){
            editText = forSearch(((ViewGroup) getView()).getChildAt(0));
        }
        return editText;
    }
    private InputState inputState;

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

    public void setListener(ActionListener listener) {
        this.listener = listener;
        inputState.setListener(listener);
    }

    public <V extends View> V getView(int id){
        return (V) getView().findViewById(id);
    }
}