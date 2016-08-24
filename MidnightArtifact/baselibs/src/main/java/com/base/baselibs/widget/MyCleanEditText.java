package com.base.baselibs.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.base.baselibs.R;

public class MyCleanEditText extends EditText implements OnFocusChangeListener {
    private Drawable mClearDrawable;
    private boolean hasFoucs;
    /**
     * 是否开启6222 2316 1256和138 8888 8888 银行卡号和电话号码显示方式 默认显示银行卡号显示方式
     */
    public boolean showType;
    /**
     * 开启电话号码显示方式
     */
    public boolean showMobileType;
    // 是否输入
    private boolean isRun = false;
    // 输入的内容
    private String inputStr = "";

    public MyCleanEditText(Context context) {
        this(context, null);
    }

    public MyCleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public MyCleanEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.ic_false);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(new TextWatcher() {
            /**
             * 当输入框里面内容发生变化的时候回调的方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int count,
                                      int after) {
                if (hasFoucs) {
                    setClearIconVisible(s.length() > 0);
                }
                show(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    /**
     * 开启银行卡号和电话号码显示方式
     */
    private void show(CharSequence s) {
        if (showType) {// 开启了银行卡号、电话号码显示方式
            if (isRun) {
                isRun = false;
                return;
            }
            isRun = true;
            inputStr = "";
            String newStr = s.toString();
            newStr = newStr.replace(" ", "");
            int index = 0;
            if (showMobileType) {// 电话号码显示方式
                if ((index + 3) < newStr.length()) {
                    inputStr += (newStr.substring(index, index + 3) + " ");
                    index += 3;
                }
            }
            while ((index + 4) < newStr.length()) {
                inputStr += (newStr.substring(index, index + 4) + " ");
                index += 4;
            }
            inputStr += (newStr.substring(index, newStr.length()));
            this.setText(inputStr);
            this.setSelection(inputStr.length());
        }
    }
}
