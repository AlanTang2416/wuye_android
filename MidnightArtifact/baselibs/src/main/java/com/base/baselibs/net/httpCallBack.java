package com.base.baselibs.net;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 15:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public interface httpCallBack {
    void clearData();
    void onBefore(Request request, int id);
    void onAfter(int id);
    void onError(Call call, Exception e,int code, int id);
    void onStringResponse(String data, Response response, int id);
    void onObjectResponse(Object data, Response response, int id);
}
