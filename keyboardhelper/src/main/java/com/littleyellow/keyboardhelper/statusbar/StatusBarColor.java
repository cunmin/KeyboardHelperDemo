package com.littleyellow.keyboardhelper.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by 小黄 on 2018/9/26.
 * 纯颜色设置状态栏，没有自己创建假状态栏控件方式
 * 在setContentView(R.layout.X)方法后调用
 */
public class StatusBarColor {

    public static int setColor(Activity activity, @ColorInt int color){
        return setColor(activity,color, StatusBarUtil.LIGHT_BAR_AOTO,Color.WHITE);
    }

    /**
     *
     * @param activity
     * @param color 状态栏颜色
     * @param fontColor 字体颜色,5.0系统下设置无效
     *             LIGHT_BAR_OFF:白色，
     *             LIGHT_BAR_ON:黑色(6.0系统下设置无效,"最初的想法是针对小米和魅族分别处理，其他系统在Android 6.0及以上才处理。经过查看用户分布(2018-02-23)Android 6.0之下占33.27%，小米用户6.69%，魅族用户1.08%。这样一看都没有必要对小米、魅族分别处理了，那就都统一到Android 6.0去设置吧。")，
     *             LIGHT_BAR_AOTO:根据颜色值自动选择
     * @return 是否成功设置颜色
     */
    public static int setColor(Activity activity, @ColorInt int color,int fontColor,@ColorInt int contentColorKITKAT){
        if (Build.VERSION.SDK_INT < KITKAT) {
            return 0;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&& StatusBarUtil.DARK_FONT_OFF != fontColor) {
                int visibility;
                if(StatusBarUtil.DARK_FONT_ON == fontColor){
                    visibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }else if (StatusBarUtil.isLightColor(color)){
                    visibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }else {
                    visibility = View.SYSTEM_UI_FLAG_VISIBLE;
                }
                activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
            }
            return StatusBarUtil.getStatusBarHeight(activity);
        }
        else{
            return KITKAT_SetColor(activity,color,contentColorKITKAT);
        }
    }

    /**
     * 主要用来修改根布局背景颜色(默认白色)，一般的需求用不到该方法
     * @param activity
     * @param stausColor
     * @param contentColor
     * @return
     */
    private static int KITKAT_SetColor(Activity activity, @ColorInt int stausColor,@ColorInt int contentColor){
        if (Build.VERSION.SDK_INT >= KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.findViewById(android.R.id.content);
            View view0 = (decorView).getChildAt(0);
            int height = StatusBarUtil.getStatusBarHeight(activity);
            if(null!=view0){
                view0.setFitsSystemWindows(true);
                ShapeDrawable statusDrawable = new ShapeDrawable();
                statusDrawable.getPaint().setColor(stausColor);
                statusDrawable.getPaint().setStyle(Paint.Style.FILL);
                ShapeDrawable contentDrawable = new ShapeDrawable();
                contentDrawable.getPaint().setColor(contentColor);
                contentDrawable.getPaint().setStyle(Paint.Style.FILL);
                Drawable[] layers = {statusDrawable,contentDrawable};
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                layerDrawable.setLayerInset(1,0,height,0,0);
                view0.setBackground(layerDrawable);
            }
            return height;
        }
        return 0;
    }

}
