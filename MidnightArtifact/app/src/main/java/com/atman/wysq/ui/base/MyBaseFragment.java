package com.atman.wysq.ui.base;

import android.content.Context;
import android.os.Bundle;

import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.base.BaseFragment;
import com.google.gson.Gson;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/1 18:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyBaseFragment extends BaseFragment {
    protected Gson mGson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showToast(String msg) {
        getBaseAppCompatActivity().showToast(msg);
    }

    public boolean isLogin() {
        return ((MyBaseActivity) getActivity()).isLogin();
    }

    public int getmWidth() {
        return ((MyBaseActivity) getActivity()).getmWidth();
    }

    public void showLogin() {
        ((MyBaseActivity) getActivity()).showLogin();
    }

    @Override
    public void clearData() {
        super.clearData();
        MyBaseApplication.getApplication().cleanLoginData();
    }

    public void toPhone(Context mContext, String phonenumber){
        if (!UiHelper.isTabletDevice(getActivity())) {
            ((MyBaseActivity) getActivity()).toPhone(mContext,phonenumber);
        } else {
            showToast("您的设备不支持拨号");
        }
    }
}
