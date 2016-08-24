package com.base.baselibs.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.base.baselibs.R;
import com.base.baselibs.adapter.PopwindowsAdapter;
import com.base.baselibs.widget.WheelView.OnWheelChangedListener;
import com.base.baselibs.widget.WheelView.OnWheelScrollListener;
import com.base.baselibs.widget.WheelView.WheelView;

import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/22 14:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SelectItemPopwindow {

    private Context context;
    private WheelView mTypeWheel;
    private PopwindowsAdapter mShopTypeAdapter;
    private String mScName;
    private int mScId;
    private int tag;
    private List<String> mList;
    private PopWindowsCallback mPopWindowsCallback;

    public SelectItemPopwindow (Context context, int tag, List<String> mList, PopWindowsCallback mPopWindowsCallback) {
        this.context = context;
        this.mList = mList;
        this.tag = tag;
        this.mPopWindowsCallback = mPopWindowsCallback;
    }

    public void showTypePopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(context).inflate(R.layout.wheel_select_item_view, null);

        final PopupWindow mSelectTypePop = new PopupWindow(contentView,
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, true);

        mTypeWheel = (WheelView) contentView.findViewById(R.id.popwin_select_wv);
        Button pop_cancel = (Button) contentView.findViewById(R.id.pop_cancel);
        Button pop_ok = (Button) contentView.findViewById(R.id.pop_ok);
        mShopTypeAdapter = new PopwindowsAdapter(context, mTypeWheel, mList, 0, 0, 0);
        mTypeWheel.setVisibleItems(5);
        mTypeWheel.setViewAdapter(mShopTypeAdapter);
        mTypeWheel.setCurrentItem(0);
        mTypeWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mTypeWheel.setViewAdapter(mShopTypeAdapter);
            }
        });
        mTypeWheel.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mScName = (String) mShopTypeAdapter.getItemText(wheel.getCurrentItem());
                mScId = wheel.getCurrentItem();
            }
        });
        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectTypePop != null) {
                    mSelectTypePop.dismiss();
                }
            }
        });
        pop_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectTypePop != null) {
                    mSelectTypePop.dismiss();
                }
                mPopWindowsCallback.selectItem(mScId, mScName, tag);
            }
        });

        mSelectTypePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        mSelectTypePop.setTouchable(true);
        mSelectTypePop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mSelectTypePop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pop_bg));
        // 设置好参数之后再show
        if (mList != null && mList.size() > 0) {
            mScName = mList.get(0);
            mScId = 0;
            mSelectTypePop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    public interface PopWindowsCallback {
        void selectItem(int id, String str, int tag);
    }
}
