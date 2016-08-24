package com.base.baselibs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.base.baselibs.util.LogUtils;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/18 17:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * 只需要重写这个方法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
