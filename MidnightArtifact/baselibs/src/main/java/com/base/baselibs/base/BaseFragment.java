package com.base.baselibs.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.base.baselibs.iimp.IInit;
import com.base.baselibs.net.httpCallBack;
import com.base.baselibs.util.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 16:14
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseFragment extends Fragment implements IInit,PullToRefreshBase.OnRefreshListener2,httpCallBack {

    /**
     * 该标志位表示第一次进入先初始化init(包含基本数据和网络获取数据)
     */
    private boolean isFirstInto = true;
    /**
     * 是否返回界面时刷新数据 默认不刷新
     */
    public boolean isRefreshNetworkData = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化
     */
    protected void init() {
        initWidget();
        initIntentAndMemData();
        doInitBaseHttp();
    }

    /**
     * 该方法会重新刷新doInitBaseHttp中所请求的数据
     */
    protected void resumeToRefreshBaseData() {
        doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstInto) {
            init();
            isFirstInto = false;
        }
        if (isRefreshNetworkData && !isFirstInto) {
            resumeToRefreshBaseData();
        }
    }

    public void showLoading() {
        getBaseAppCompatActivity().showLoading();
    }

    public void cancelLoading() {
        getBaseAppCompatActivity().cancelLoading();
    }

    public void showToast(String text) {
        getBaseAppCompatActivity().showToast(text);
    }

    protected BaseAppCompatActivity getBaseAppCompatActivity() {
        return (BaseAppCompatActivity) getActivity();
    }

    /**
     * 初始化可支持上下啦的控件
     *
     * @param refreshMode       刷新模式从PullMode中间取
     * @param pullToRefreshBase 基类View 可传多个不同的View
     */
    protected void initRefreshView(PullToRefreshBase.Mode refreshMode, PullToRefreshBase... pullToRefreshBase) {
        getBaseAppCompatActivity().initRefreshView(refreshMode, this, pullToRefreshBase);
    }

    /**
     * 数据加载完调用
     *
     * @param refreshMode 刷新完成后设置下拉上啦模式
     */
    protected void onLoad(PullToRefreshBase.Mode refreshMode, PullToRefreshBase... pullToRefreshBase) {
        //有时候getActivity null
        try {
            getBaseAppCompatActivity().onLoad(refreshMode, pullToRefreshBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {}

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {}

    @Override
    public void initIntentAndMemData() {

    }

    @Override
    public void doInitBaseHttp() {

    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    public void initWidget(View... v) {

    }

    @Override
    public void clearData() {

    }

    @Override
    public void onBefore(Request request, int id) {

    }

    @Override
    public void onAfter(int id) {

    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        LogUtils.e("返回码："+code+"，id:"+id);
        if (id!=10) {
            showToast(e.toString().replace("java.io.IOException: ",""));
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {

    }

    @Override
    public void onObjectResponse(Object data, Response response, int id) {

    }
}
