package com.atman.wysq.utils;

import android.app.Activity;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/13 09:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ShareHelper {

    public static void share(Activity context, SHARE_MEDIA Platform,
                             String withTargetUrl , UMShareListener mUMShareListener) {
        if (Platform == SHARE_MEDIA.SINA && !Tools.isPkgInstalled(context, "com.sina.weibo")) {
            ((MyBaseActivity)context).showToast("尚未安装新浪客户端，无法分享");
            return;
        }
        if (Platform == SHARE_MEDIA.QQ && !Tools.isPkgInstalled(context, "com.tencent.mobileqq")) {
            ((MyBaseActivity)context).showToast("尚未安装QQ客户端，无法分享");
            return;
        }
        if (Platform == SHARE_MEDIA.WEIXIN && !Tools.isPkgInstalled(context, "com.tencent.mm")) {
            ((MyBaseActivity)context).showToast("尚未安装微信客户端，无法分享");
            return;
        }
        new ShareAction(context)
                .setPlatform(Platform)
                .setCallback(mUMShareListener)
                .withText("发现一个挺好玩的APP，快来看看吧！"+withTargetUrl)
                .withTitle("午夜神器")
                .withMedia(new UMImage(context, R.mipmap.ic_launcher))
                .withTargetUrl(withTargetUrl)
                .share();
    }
}
