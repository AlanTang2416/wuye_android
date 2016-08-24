package com.base.baselibs.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 描述 防止多次显示的Toast
 * 作者 tangbingliang
 * 时间 16/5/20 16:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CustomToast {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration);

        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

}
