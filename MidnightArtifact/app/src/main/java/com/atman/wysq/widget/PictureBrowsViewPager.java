package com.atman.wysq.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/22 15:12
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PictureBrowsViewPager extends ViewPager {

    private static final String TAG = "PictureBrowsViewPager";

    public PictureBrowsViewPager(Context context) {
        super(context);
    }

    public PictureBrowsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {

            Log.e(TAG, "hacky viewpager error1");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {

            Log.e(TAG, "hacky viewpager error2");
            return false;
        }
    }

}
