package com.littleyellow.keyboardhelper;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;

import com.littleyellow.keyboardhelper.utils.KBSharedPreferences;
import com.littleyellow.keyboardhelper.utils.KeyboardListener;

import static com.littleyellow.keyboardhelper.utils.KeyboardListener.getDisplayVisibleFrameHeight;

/**
 * Created by 小黄 on 2018/10/9.
 */

public class KeyboardHelper {
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private KeyboardListener.OnInputMethodListener onInputMethodListener;
    private Rect windowContentRect;
    private Rect keyboardRect;
    private Activity host;

    private KeyboardHelper(Builder builder) {
        host = builder.host;
        onInputMethodListener = builder.onInputMethodListener;
        if(null==host){
            return;
        }
        final View decorView = host.getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                onAttach(host);
                host.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    }
                    @Override
                    public void onActivityStarted(Activity activity) {
                        if (host == activity && onGlobalLayoutListener == null) {
                            onAttach(activity);
                        }
                    }
                    @Override
                    public void onActivityResumed(Activity activity) {
                        if (host == activity && onGlobalLayoutListener == null) {
                            throw new IllegalStateException("assistActivity() must be called before onStart() called!");
                        }
                    }
                    @Override
                    public void onActivityPaused(Activity activity) {
                    }
                    @Override
                    public void onActivityStopped(Activity activity) {
                    }
                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }
                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        if (activity == host) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                onDetach(activity);
                            }
                            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                        }
                    }
                });
            }
        });
    }

    private void onAttach(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        windowContentRect = getDisplayVisibleFrameHeight(decorView);
        onGlobalLayoutListener =  new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect displayVisibleFrame = getDisplayVisibleFrameHeight(decorView);
                if (keyboardRect == null) {
                    keyboardRect = new Rect(displayVisibleFrame);
                }
                keyboardRect.top = displayVisibleFrame.bottom;
                keyboardRect.bottom = windowContentRect.bottom;
                KBSharedPreferences.setDefKeyboardHeight(decorView.getContext(),keyboardRect.bottom-keyboardRect.top);
                if (onInputMethodListener != null) {
                    onInputMethodListener.onInputMethodStatusChanged(keyboardRect, keyboardRect.height() != 0);
                }
            }
        };
        decorView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }
    @RequiresApi(16)
    private void onDetach(Activity activity) {
        if (onInputMethodListener != null) {
            activity.getWindow()
                    .getDecorView()
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Activity host;
        private KeyboardListener.OnInputMethodListener onInputMethodListener;

        private Builder() {
        }

        public Builder host(Activity host) {
            this.host = host;
            return this;
        }

        public Builder onInputMethodListener(KeyboardListener.OnInputMethodListener onInputMethodListener) {
            this.onInputMethodListener = onInputMethodListener;
            return this;
        }

        public KeyboardHelper build() {
            return new KeyboardHelper(this);
        }
    }
}
