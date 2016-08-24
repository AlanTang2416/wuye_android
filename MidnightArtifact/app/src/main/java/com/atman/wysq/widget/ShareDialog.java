package com.atman.wysq.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.base.baselibs.widget.BottomDialog;

/**
 * 分享对话框
 *
 * @author fanxi
 * @version 1
 * @email fancy2110@gmail.com
 * @since 2/26/16
 */
public class ShareDialog extends BottomDialog {

    private ShareListener mShareListener;

    protected ShareDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_invite_friends, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        view.findViewById(R.id.invite_friends_weixin_ll).setOnClickListener(widgetClick);
        view.findViewById(R.id.invite_friends_pyq_ll).setOnClickListener(widgetClick);
        view.findViewById(R.id.invite_friends_weibo_ll).setOnClickListener(widgetClick);
        view.findViewById(R.id.invite_friends_qq_ll).setOnClickListener(widgetClick);
        view.findViewById(R.id.invite_friends_copy_ll).setOnClickListener(widgetClick);
        addView(view, params);
    }

    public void setShareListener(ShareListener listener) {
        this.mShareListener = listener;
    }

    private View.OnClickListener widgetClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShareDialog.this.dismiss();
            if (mShareListener != null) {
                switch (v.getId()) {
                    case R.id.invite_friends_weixin_ll:
                        mShareListener.onShare(1);
                        break;
                    case R.id.invite_friends_pyq_ll:
                        mShareListener.onShare(2);
                        break;
                    case R.id.invite_friends_weibo_ll:
                        mShareListener.onShare(3);
                        break;
                    case R.id.invite_friends_qq_ll:
                        mShareListener.onShare(4);
                        break;
                    case R.id.invite_friends_copy_ll:
                        mShareListener.onShare(5);
                        break;
                    default:
                        break;

                }
            }
        }
    };

    public static class Builder extends BottomDialog.Builder {
        private ShareListener mShareListener;

        public Builder(Context context) {
            super(context);
        }

        @Override
        protected ShareDialog createDialog(Context context) {
            return new ShareDialog(context);
        }

        public void setOnShareListener(ShareListener listener) {
            mShareListener = listener;
        }

        @Override
        public ShareDialog build() {
            ShareDialog shareDialog =  (ShareDialog) super.build();
            shareDialog.setShareListener(mShareListener);
            return shareDialog;
        }

        @Override
        public ShareDialog show() {
            return (ShareDialog) super.show();
        }

    }

    public interface ShareListener {
        void onShare(int type);
    }
}
