package com.base.baselibs.net;

import android.content.Context;

import com.base.baselibs.base.BaseAppCompatActivity;
import com.tbl.okhttputils.callback.Callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 15:42
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyCallback extends Callback {

    private httpCallBack CallBack;
    private Context mContext;
    private Class clazz;
    private boolean isShowLoading;

    public MyCallback(){}

    public MyCallback(Context mContext, httpCallBack CallBack, boolean isShowLoading){
        this.CallBack = CallBack;
        this.mContext = mContext;
        this.isShowLoading = isShowLoading;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        ((BaseAppCompatActivity)mContext).showLoading();
        CallBack.onBefore(request, id);
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        ((BaseAppCompatActivity)mContext).cancelLoading();
        CallBack.onAfter(id);
    }

    @Override
    public Object parseNetworkResponse(Response response, int id) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        CallBack.onError(call, e, code, id);
    }

    @Override
    public void onResponse(Object data, Response response, int id) {
        CallBack.onObjectResponse(data, response, id);
    }
}
