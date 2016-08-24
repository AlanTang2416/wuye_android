package com.base.baselibs.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.baselibs.R;
import com.base.baselibs.adapter.DialogAdapter;


/**
 * Created by fanxi on 5/7/15.
 */
public class PromptDialog extends Dialog implements
        View.OnClickListener, OnItemClickListener {

    protected CharSequence mPositiveButtonText;

    protected OnClickListener mPositiveButtonListener;

    protected CharSequence mNegativeButtonText;

    protected OnClickListener mNegativeButtonListener;

    protected CharSequence mNeutralButtonText;

    protected OnClickListener mNeutralButtonListener;

    protected OnCancelListener mOnCancelListener;

    protected boolean mCancelable;

    protected CharSequence[] mItems;

    protected OnClickListener mOnClickListener;

    protected OnKeyListener mOnKeyListener;

    protected OnDismissListener mOnDismissListener;

    protected CharSequence mTitle;

    protected CharSequence mMessage;

    protected Bitmap mImage;
    protected String mTag;
    protected ViewGroup mRootView;
    protected LinearLayout mContentView;
    protected TextView mTitleView;
    protected TextView mMessageView;

    public PromptDialog(Context context) {
        super(context, R.style.dialog_center);
    }

    public PromptDialog(Context context, int theme) {
        super(context, R.style.dialog_center);
    }

    protected PromptDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, R.style.dialog_center);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
                int y = (int) event.getY();
                int x = (int) event.getX();
                boolean touchOutSide = x < mRootView.getLeft() ||
                        x > mRootView.getRight() || y < mRootView.getTop()
                        || y > mRootView.getBottom();

                if (touchOutSide && mCancelable) {
                    dismiss();
                }
                break;
            default:
        }

        return super.onTouchEvent(event);
    }

    public void addView(View view) {
        if (mRootView == null) {
            throw new IllegalStateException("you must call onCreate before addView");
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        mContentView.addView(view, params);
    }

    /**
     * 通过该方法添加的view将直接复用父类的title 和 button样式，只改变内容区的布局
     *
     * @param view    , 自定义内容去
     * @param params, 自定义内容区的layout 参数
     */
    public void addView(View view, LinearLayout.LayoutParams params) {
        if (mRootView == null) {
            throw new IllegalStateException("you must call onCreate before addView");
        }

        mContentView.addView(view, params);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        mTitle = getContext().getString(titleId);
        if (mTitleView != null) {
            mTitleView.setText(mTitle);
        }
    }

    public void setMessage(CharSequence message) {
        mMessage = message;
        if (mMessageView != null) {
            mMessageView.setText(message);
        }
    }

    public void setMessage(int message) {
        mMessage = getContext().getString(message);
        if (mMessageView != null) {
            mMessageView.setText(mMessage);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_common);

        mRootView = (RelativeLayout) findViewById(R.id.root_content);
        mTitleView = (TextView) findViewById(R.id.title_text);
        if (!TextUtils.isEmpty(mTitle)) {
            mTitleView.setText(mTitle);
        } else {
            mTitleView.setVisibility(View.GONE);
        }

        mContentView = (LinearLayout) findViewById(R.id.content);
        Context context = getContext();

        if (mItems == null) {
            if (!TextUtils.isEmpty(mMessage)) {
                initMessageView(getContext(), mMessage);
            }

            if (mImage != null) {
                initImageView(getContext(), mImage);
            }
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = new ListView(context);
            listView.setOnItemClickListener(this);
            listView.setCacheColorHint(Color.TRANSPARENT);
            Resources resources = context.getResources();

            listView.setDividerHeight(dip2px(context, 1));
            Drawable drawable = resources.getDrawable(R.drawable.bg_list_selector);
            if (drawable != null) {
                listView.setSelector(drawable);
            }

            mContentView.addView(listView, params);
            listView.setAdapter(new DialogAdapter(context, mItems));
        }

        View buttonPanel = findViewById(R.id.buttons);
        buttonPanel.setVisibility(View.GONE);

        boolean negativeEnable = false;
        boolean positiveEnable = false;
        boolean neutralEnable = false;

        Button negativeButton = (Button) findViewById(R.id.btn_negative);
        if (TextUtils.isEmpty(mNegativeButtonText)) {
            negativeButton.setVisibility(View.GONE);
        } else {
            negativeEnable = true;
            buttonPanel.setVisibility(View.VISIBLE);
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(mNegativeButtonText);
            negativeButton.setOnClickListener(this);
        }

        Button positiveButton = (Button) findViewById(R.id.btn_positive);
        if (TextUtils.isEmpty(mPositiveButtonText)) {
            positiveButton.setVisibility(View.GONE);
        } else {
            positiveEnable = true;
            buttonPanel.setVisibility(View.VISIBLE);
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setText(mPositiveButtonText);
            positiveButton.setOnClickListener(this);
        }

        Button neutralButton = (Button) findViewById(R.id.btn_neutral);
        if (TextUtils.isEmpty(mNeutralButtonText)) {
            neutralButton.setVisibility(View.GONE);
        } else {
            neutralEnable = true;
            buttonPanel.setVisibility(View.VISIBLE);
            neutralButton.setVisibility(View.VISIBLE);
            neutralButton.setText(mNeutralButtonText);
            neutralButton.setOnClickListener(this);
        }

        View leftDivider = findViewById(R.id.positive_devide);
        View rightDivider = findViewById(R.id.neutral_devide);

        if (positiveEnable) {
            if (negativeEnable) {
                if (neutralEnable) {
                    positiveButton.setBackgroundResource(R.drawable.dialog_left_button_selector);
                    leftDivider.setVisibility(View.VISIBLE);
                    negativeButton.setBackgroundResource(R.drawable.dialog_right_button_selector);
                    rightDivider.setVisibility(View.VISIBLE);
                    neutralButton.setBackgroundResource(R.drawable.dialog_middle_button_selector);
                } else {
                    positiveButton.setBackgroundResource(R.drawable.dialog_left_button_selector);
                    leftDivider.setVisibility(View.VISIBLE);
                    rightDivider.setVisibility(View.GONE);
                    negativeButton.setBackgroundResource(R.drawable.dialog_right_button_selector);
                }
            } else {
                if (neutralEnable) {
                    positiveButton.setBackgroundResource(R.drawable.dialog_left_button_selector);
                    leftDivider.setVisibility(View.VISIBLE);
                    rightDivider.setVisibility(View.GONE);
                    neutralButton.setBackgroundResource(R.drawable.dialog_right_button_selector);
                } else {
                    rightDivider.setVisibility(View.GONE);
                    leftDivider.setVisibility(View.GONE);
                    positiveButton.setBackgroundResource(R.drawable.dialog_bottom_selector);
                }
            }
        } else {
            if (negativeEnable) {
                if (neutralEnable) {
                    neutralButton.setBackgroundResource(R.drawable.dialog_left_button_selector);
                    leftDivider.setVisibility(View.VISIBLE);
                    rightDivider.setVisibility(View.GONE);
                    negativeButton.setBackgroundResource(R.drawable.dialog_right_button_selector);
                } else {
                    negativeButton.setBackgroundResource(R.drawable.dialog_bottom_selector);
                    rightDivider.setVisibility(View.GONE);
                    leftDivider.setVisibility(View.GONE);
                }
            } else {
                if (neutralEnable) {
                    neutralButton.setBackgroundResource(R.drawable.dialog_bottom_selector);
                    rightDivider.setVisibility(View.GONE);
                    leftDivider.setVisibility(View.GONE);
                }
            }
        }

        setOnCancelListener(mOnCancelListener);
        setOnDismissListener(mOnDismissListener);
        setCancelable(mCancelable);
        setOnKeyListener(mOnKeyListener);
        View view = getWindow().getDecorView();
        view.requestLayout();
    }

    protected void initImageView(Context context, Bitmap mImage) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        ImageView iconView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER_HORIZONTAL;
        int minWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, metrics);
        int imageWidth = mImage.getWidth();

        if (imageWidth < minWidth) {
            int height = mImage.getHeight() * minWidth / imageWidth;
            iconView.setMinimumHeight(height);
            iconView.setMinimumWidth(minWidth);
        }
        iconView.setImageBitmap(mImage);
        iconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mContentView.addView(iconView, params);
    }

    protected void initMessageView(Context context, CharSequence mMessage) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mMessageView = new TextView(context);
        mMessageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mMessageView.setTextColor(context.getResources().getColor(R.color.dialog_message_color));
        float add = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4, metrics);
        mMessageView.setLineSpacing(add, 1f);
        mMessageView.setText(mMessage);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mContentView.addView(mMessageView, params);
    }


    public String getTag() {
        return mTag;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_positive) {
            if (mPositiveButtonListener != null) {
                mPositiveButtonListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
            } else {
                dismiss();
            }
        } else if (id == R.id.btn_negative) {
            if (mNegativeButtonListener != null) {
                mNegativeButtonListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
            } else {
                dismiss();
            }

        } else if (id == R.id.btn_neutral) {
            if (mNeutralButtonListener != null) {
                mNeutralButtonListener.onClick(this, DialogInterface.BUTTON_NEUTRAL);
            } else {
                dismiss();
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this, position);
        }
    }


public static class Builder {

    private Context mContext;

    private CharSequence mPositiveButtonText;

    private OnClickListener mPositiveButtonListener;

    private CharSequence mNegativeButtonText;

    private OnClickListener mNegativeButtonListener;

    private CharSequence mNeutralButtonText;

    private OnClickListener mNeutralButtonListener;

    private OnCancelListener mOnCancelListener;

    private boolean mCancelable = true;

    private CharSequence[] mItems;

    private OnClickListener mOnClickListener;

    private OnKeyListener mOnKeyListener;

    private OnDismissListener mOnDismissListener;

    private CharSequence mTitle;

    private CharSequence mMessage;

    private Bitmap mBitmap;

    private String mTag;

    public Builder(Context context) {
        mContext = context;
    }


    protected PromptDialog createDialog(Context context) {
        return new PromptDialog(context);
    }

    public PromptDialog build() {
        PromptDialog dialog = createDialog(mContext);
        dialog.mOnClickListener = mOnClickListener;
        dialog.mOnCancelListener = mOnCancelListener;
        dialog.mOnDismissListener = mOnDismissListener;
        dialog.mTitle = mTitle;
        dialog.mMessage = mMessage;
        dialog.mOnClickListener = mOnClickListener;
        dialog.mItems = mItems;
        dialog.mNeutralButtonListener = mNeutralButtonListener;
        dialog.mPositiveButtonListener = mPositiveButtonListener;
        dialog.mNegativeButtonListener = mNegativeButtonListener;
        dialog.mNegativeButtonText = mNegativeButtonText;
        dialog.mPositiveButtonText = mPositiveButtonText;
        dialog.mNeutralButtonText = mNeutralButtonText;
        dialog.mCancelable = mCancelable;
        dialog.mOnKeyListener = mOnKeyListener;
        dialog.mImage = mBitmap;
        dialog.mTag = mTag;
        return dialog;
    }


    public PromptDialog show() {
        PromptDialog dialog = build();

        dialog.show();

        return dialog;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog_pay is pressed.
     *
     * @param textId   The resource id of the text to display in the positive button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setPositiveButton(int textId, final OnClickListener listener) {
        mPositiveButtonText = mContext.getText(textId);
        mPositiveButtonListener = listener;
        return this;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog_pay is pressed.
     *
     * @param text     The text to display in the positive button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
        mPositiveButtonText = text;
        mPositiveButtonListener = listener;
        return this;
    }

    /**
     * Set a listener to be invoked when the negative button of the dialog_pay is pressed.
     *
     * @param textId   The resource id of the text to display in the negative button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setNegativeButton(int textId, final OnClickListener listener) {
        mNegativeButtonText = mContext.getText(textId);
        mNegativeButtonListener = listener;
        return this;
    }

    /**
     * Set a listener to be invoked when the negative button of the dialog_pay is pressed.
     *
     * @param text     The text to display in the negative button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setNegativeButton(CharSequence text, final OnClickListener listener) {
        mNegativeButtonText = text;
        mNegativeButtonListener = listener;
        return this;
    }

    /**
     * Set a listener to be invoked when the neutral button of the dialog_pay is pressed.
     *
     * @param textId   The resource id of the text to display in the neutral button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setNeutralButton(int textId, final OnClickListener listener) {
        mNeutralButtonText = mContext.getText(textId);
        mNeutralButtonListener = listener;
        return this;
    }

    /**
     * Set a listener to be invoked when the neutral button of the dialog_pay is pressed.
     *
     * @param text     The text to display in the neutral button
     * @param listener The {@link OnClickListener} to use.
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setNeutralButton(CharSequence text, final OnClickListener listener) {
        mNeutralButtonText = text;
        mNeutralButtonListener = listener;
        return this;
    }

    /**
     * Sets whether the dialog_pay is cancelable or not.  Default is true.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    /**
     * Sets the callback that will be called if the dialog_pay is canceled.
     * <p/>
     * <p>Even in a cancelable dialog_pay, the dialog_pay may be dismissed for reasons other than
     * being canceled or one of the supplied choices being selected.
     * If you are interested in listening for all cases where the dialog_pay is dismissed
     * and not just when it is canceled, see
     * {@link #setOnDismissListener(OnDismissListener)
     * setOnDismissListener}.</p>
     *
     * @return This Builder object to allow for chaining of calls to set methods
     * @see #setCancelable(boolean)
     * @see #setOnDismissListener(OnDismissListener)
     */
    public Builder setOnCancelListener(OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
        return this;
    }

    /**
     * Sets the callback that will be called when the dialog_pay is dismissed for any reason.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }


    /**
     * Sets the callback that will be called if a key is dispatched to the dialog_pay.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setOnKeyListener(OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog_pay as the content, you will be notified
     * of
     * the
     * selected item via the supplied listener. This should be an array type i.e. R.array.foo
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setItems(int itemsId, final OnClickListener listener) {
        mItems = mContext.getResources().getTextArray(itemsId);
        mOnClickListener = listener;
        return this;
    }

    /**
     * Set a list of items to be displayed in the dialog_pay as the content, you will be notified
     * of
     * the
     * selected item via the supplied listener.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setItems(CharSequence[] items, final OnClickListener listener) {
        mItems = items;
        mOnClickListener = listener;
        return this;
    }

    /**
     * Set the title using the given resource id.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setTitle(int titleId) {
        mTitle = mContext.getText(titleId);
        return this;
    }

    /**
     * Set the title displayed in the {@link Dialog}.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setTitle(CharSequence title) {
        mTitle = title;
        return this;
    }

    /**
     * Set the message to display using the given resource id.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setMessage(int messageId) {
        mMessage = mContext.getText(messageId);
        return this;
    }

    /**
     * Set the message to display.
     *
     * @return This Builder object to allow for chaining of calls to set methods
     */
    public Builder setMessage(CharSequence message) {
        mMessage = message;
        return this;
    }


    public Builder setImage(int imageId) {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), imageId);
        return this;
    }

    public Builder setImage(Bitmap image) {
        mBitmap = image;
        return this;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public void reset() {
        mBitmap = null;
        mMessage = null;
        mTitle = null;
        mOnClickListener = null;
        mOnCancelListener = null;
        mOnDismissListener = null;
        mItems = null;
        mNeutralButtonListener = null;
        mPositiveButtonListener = null;
        mNegativeButtonListener = null;
        mNegativeButtonText = null;
        mPositiveButtonText = null;
        mNeutralButtonText = null;
        mCancelable = false;
        mOnKeyListener = null;
        mTag = null;
    }
}


}
