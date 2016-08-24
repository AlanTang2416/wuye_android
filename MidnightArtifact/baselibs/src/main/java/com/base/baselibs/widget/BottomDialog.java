package com.base.baselibs.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.base.baselibs.R;
import com.base.baselibs.adapter.DialogAdapter;

/**
 * Created by fancy on 2015/3/27.
 * 底部弹窗对话框
 */
public class BottomDialog extends PromptDialog implements
        View.OnClickListener, AdapterView.OnItemClickListener {

    private boolean mAutoDismissAfterClick = true;

    protected BottomDialog(Context context) {
        this(context, true, null);
    }

    protected BottomDialog(Context context, int theme) {
        this(context, true, null);
    }

    protected BottomDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, R.style.dialog_bottom);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
        setCanceledOnTouchOutside(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
                int y = (int) event.getY();
                if (y < mRootView.getTop() && mCancelable) {
                    dismiss();
                }
                break;
            default:
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_bottom);
        mRootView = (LinearLayout) findViewById(R.id.dialog_content);

        mTitleView = (TextView) findViewById(R.id.title_text);
        View titleBar = findViewById(R.id.title_bar);
        if (!TextUtils.isEmpty(mTitle)) {
            titleBar.setVisibility(View.VISIBLE);
            mTitleView.setText(mTitle);
        } else {
            titleBar.setVisibility(View.GONE);
        }

        TextView negativeButton = (TextView) findViewById(R.id.btn_negative);
        if (TextUtils.isEmpty(mNegativeButtonText)) {
            negativeButton.setVisibility(View.GONE);
        } else {
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(mNegativeButtonText);
            negativeButton.setOnClickListener(this);
        }

        TextView positiveButton = (TextView) findViewById(R.id.btn_positive);
        if (TextUtils.isEmpty(mPositiveButtonText)) {
            positiveButton.setVisibility(View.GONE);
        } else {
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setText(mPositiveButtonText);
            positiveButton.setOnClickListener(this);
        }

        View neutralButton = null;
        if (!TextUtils.isEmpty(mNeutralButtonText)) {
            neutralButton = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_dialog_footer, null);

            TextView label = (TextView) neutralButton.findViewById(R.id.btn_neutral);
            label.setOnClickListener(this);
            label.setText(mNeutralButtonText);
        }

        mContentView = (LinearLayout) findViewById(R.id.content);
        Context context = getContext();

        if (mItems == null) {
            if (!TextUtils.isEmpty(mMessage)) {
                initMessageView(context, mMessage);
            }

            if (mImage != null) {
                initImageView(context, mImage);
            }

            if (neutralButton != null) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                mRootView.addView(neutralButton, params);
            }
        } else {
            mContentView.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = new ListView(getContext());
            listView.setCacheColorHint(Color.TRANSPARENT);
            Resources resources = getContext().getResources();
            listView.setFooterDividersEnabled(false);
            listView.setDivider(resources.getDrawable(R.drawable.gray_divider));
            listView.setDividerHeight(dip2px(getContext(), 0.5f));
            Drawable drawable = resources.getDrawable(R.drawable.bg_list_selector);
            if (drawable != null) {
                listView.setSelector(drawable);
            }

            if (neutralButton != null) {
                listView.addFooterView(neutralButton);
            }

            listView.setOnItemClickListener(this);
            mContentView.addView(listView, params);
            listView.setAdapter(new DialogAdapter(getContext(), mItems));
        }

        if (mOnCancelListener != null) {
            setOnCancelListener(mOnCancelListener);
        }

        setCancelable(mCancelable);
        if (mOnKeyListener != null) {
            setOnKeyListener(mOnKeyListener);
        }

        if (mOnDismissListener != null) {
            setOnDismissListener(mOnDismissListener);
        }
    }


    /**
     * 设置当点击事件发生时对话框是否需要自动关闭
     * 默认关闭
     *
     * @param enable, true 关闭， false 不关闭
     **/
    public void setAutoDismissEnable(boolean enable) {
        mAutoDismissAfterClick = enable;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        if (mAutoDismissAfterClick) {
            dismiss();
        }
    }


    public static class Builder extends PromptDialog.Builder{

        public Builder(Context context) {
            super(context);
        }

        protected BottomDialog createDialog(Context context) {
            return new BottomDialog(context);
        }

        public BottomDialog build() {
            return (BottomDialog)super.build();
        }

        public BottomDialog show() {
            BottomDialog dialog = build();
            dialog.show();

            return dialog;
        }
    }
}
