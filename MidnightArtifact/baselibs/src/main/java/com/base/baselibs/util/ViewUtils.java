package com.base.baselibs.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.baselibs.base.BaseApplication;

/**
 * Created by GodXj on 2015/12/24.
 */
public class ViewUtils {
    /**
     * 将图片加入TextView
     *
     * @param view
     * @param bitmap
     * @param content
     */
    public static void setImageToTextView(Context context, TextView view,
                                          Bitmap bitmap, String content) {
        ImageSpan imgSpan = new ImageSpan(context, bitmap);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spanString);
        view.append("");
    }

    /**
     * 给ImageView设置src
     */
    public static void setIVSrc(ImageView iv, int resDefault, int resFocus) {
        StateListDrawable drawable = getSelector(resDefault,
                resFocus);
        iv.setImageDrawable(drawable);
    }

    /**
     * 给View设置src
     * position 1左2上3右4下
     */
    public static void setViewDrawableSrc(TextView iv, int resDefault, int resFocus, int position) {
        StateListDrawable drawable = getSelector(resDefault,
                resFocus);
        switch (position) {
            case 1:
                iv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                iv.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                iv.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                iv.setCompoundDrawables(null, null, null, drawable);
                break;

            default:
                break;
        }
    }

    /**
     * 动态获取点击布局XML
     *
     * @param resDefault
     * @param resFocus
     * @return
     */
    public static StateListDrawable getSelector(int resDefault, int resFocus) {
        Drawable resDefaultDrawable = BaseApplication.getmContext().getResources().getDrawable(resDefault);
        resDefaultDrawable.setBounds(0, 0, resDefaultDrawable.getMinimumWidth(),
                resDefaultDrawable.getMinimumHeight());
        Drawable resFocusDrawable = BaseApplication.getmContext().getResources().getDrawable(resFocus);
        resFocusDrawable.setBounds(0, 0, resFocusDrawable.getMinimumWidth(),
                resFocusDrawable.getMinimumHeight());
        StateListDrawable drawable = getSelector(resDefaultDrawable, resFocusDrawable);
        return drawable;
    }

    /**
     * 通过Drawable创建点击效果
     *
     * @param resDefaultDrawable 默认的Drawable
     * @param resFocusDrawable   点击或者获取焦点后的Drawable
     * @return
     */
    public static StateListDrawable getSelector(Drawable resDefaultDrawable, Drawable resFocusDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(
                new int[]{-android.R.attr.state_focused,
                        -android.R.attr.state_selected,
                        -android.R.attr.state_pressed}, resDefaultDrawable);
        drawable.addState(
                new int[]{-android.R.attr.state_focused,
                        android.R.attr.state_selected,
                        -android.R.attr.state_pressed}, resFocusDrawable);
        drawable.addState(
                new int[]{android.R.attr.state_focused,
                        -android.R.attr.state_selected,
                        -android.R.attr.state_pressed}, resFocusDrawable);
        drawable.addState(
                new int[]{android.R.attr.state_focused,
                        android.R.attr.state_selected,
                        -android.R.attr.state_pressed}, resFocusDrawable);
        drawable.addState(new int[]{android.R.attr.state_selected,
                android.R.attr.state_pressed}, resFocusDrawable);
        drawable.addState(
                new int[]{android.R.attr.state_pressed}, resFocusDrawable);
        return drawable;
    }

    /**
     * 获取Shape
     *
     * @param strokeWidth 边框宽度
     * @param roundRadius 圆角半径
     * @param strokeColor 边框颜色
     * @param fillColor   内部填充颜色
     * @return
     */
    public static GradientDrawable getShape(int strokeWidth, int roundRadius, int strokeColor, int fillColor) {
        return getShape(strokeWidth, roundRadius, strokeColor, new int[]{fillColor, fillColor, fillColor});
    }

    /**
     * 获取Shape
     *
     * @param strokeWidth 边框宽度
     * @param roundRadius 圆角半径
     * @param strokeColor 边框颜色
     * @param colors      内部填充颜色 //分别为开始颜色，中间夜色，结束颜色
     * @return
     */
    public static GradientDrawable getShape(int strokeWidth, int roundRadius, int strokeColor, int[] colors) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }
}
