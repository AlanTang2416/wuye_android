package com.base.baselibs.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.baselibs.R;
import com.base.baselibs.util.DensityUtil;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 15:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class WaitingDialog extends MyDialog {
    private Context context;
    private ImageView imageView;
    private AnimationDrawable anim;
    private View view;

    /*
     * private ImageView anim_img; private TextView content_tv;
     */
    public WaitingDialog(Context context) {
        super(context, R.style.DT_DIALOG_Translucent);
        this.context = context;
        view = LayoutInflater.from(context).inflate(
                R.layout.dt_now_page_loading, null);
        imageView = (ImageView) view.findViewById(R.id.loding_img);
        anim = (AnimationDrawable) context.getResources().getDrawable(
                R.drawable.loading_drawable);
        imageView.setImageDrawable(anim);
        super.createView();
        super.setCancelable(true);
        super.setCanceledOnTouchOutside(true);
        setWindowBgAlpha(0);
        int width = DensityUtil.dp2px(context, 80);
        setWindowSize(width, width);
    }

    @Override
    protected View getView() {
        // imageView.setImageResource(R.drawable.loading_drawable);
		/*
		 * anim_img = (ImageView) view.findViewById(R.id.anim_img); content_tv =
		 * (TextView) view.findViewById(R.id.content_tv);
		 */
        return view;
    }

    public void startAnimation() {
        if (!anim.isRunning()) {
            anim.start();
        }

    }

    public void stopAnimation() {
        if (anim.isRunning()) {
            anim.stop();
        }
    }

    public ImageView getAnim_img() {
        return imageView;
    }

    public TextView getContent_tv() {
        return new TextView(context);
    }
}
