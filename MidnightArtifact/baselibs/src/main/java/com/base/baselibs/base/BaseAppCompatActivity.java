package com.base.baselibs.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.base.baselibs.R;
import com.base.baselibs.iimp.IInit;
import com.base.baselibs.net.BaseErrorModel;
import com.base.baselibs.net.BaseNormalModel;
import com.base.baselibs.net.httpCallBack;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.WaitingDialog;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import third.me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 15:33
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseAppCompatActivity extends SwipeBackActivity
        implements IInit, PullToRefreshBase.OnRefreshListener2,httpCallBack {

    public WaitingDialog mWaitingDialog = null;
    private boolean isFirstInto = true;//该标志位表示第一次进入先初始化init(包含基本数据和网络获取数据)
    public boolean isRefreshNetworkData = false;//是否返回界面时刷新数据 默认不刷新
    private static long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏bar
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstInto) {
            init();
            isFirstInto = false;
        }
        if (isRefreshNetworkData && !isFirstInto) {
            resumeToRefreshBaseData();
        }
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 防止快速点击,启动多个同样的界面
     *
     * @return
     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    /**
     * 防止快速点击,启动多个同样的界面
     *
     * @return
     */
//    public static boolean isFastDoubleClick() {
//        long now = System.currentTimeMillis();
//        long timeD = now - lastClickTime;
//        lastClickTime = now;
//        return timeD <= 500;
//    }

    /**
     * 开启加载Loading
     */
    public void showLoading() {
        if (mWaitingDialog == null) {
            mWaitingDialog = new WaitingDialog(this);
        }
        mWaitingDialog.setCanceledOnTouchOutside(true);
        mWaitingDialog.startAnimation();
        mWaitingDialog.setCancelable(true);
        if (mWaitingDialog.isShowing()) {
            return;
        }
        if (!isFinishing()) {
            try {
                mWaitingDialog.show();
            } catch (Exception e) {

            }

        }
    }

    /**
     * 关闭加载Loading
     */
    public void cancelLoading() {
        try {
            if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
                mWaitingDialog.stopAnimation();
                mWaitingDialog.dismiss();
            }
        } catch (Exception e){
        }
    }

    private Toast mToast;
    public void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(BaseApplication.getmContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    /**
     * 初始化可支持上下啦的控件
     *
     * @param refreshMode       刷新模式从PullMode中间取
     * @param pullToRefreshBase 基类View 可传多个不同的View
     */
    protected void initRefreshView(PullToRefreshBase.Mode refreshMode, PullToRefreshBase... pullToRefreshBase) {
        if (pullToRefreshBase != null) {
            for (PullToRefreshBase refreshBase : pullToRefreshBase) {
                refreshBase.setOnRefreshListener(this);
                refreshBase.onRefreshComplete();
                refreshBase.setMode(refreshMode);
            }
        } else {
            showToast("请实例化对象");
        }
    }

    /**
     * 初始化可支持上下啦的控件
     *
     * @param refreshMode       刷新模式从PullMode中间取
     * @param pullToRefreshBase 基类View 可传多个不同的View
     */
    protected void initRefreshView(PullToRefreshBase.Mode refreshMode, PullToRefreshBase.OnRefreshListener2 listener2, PullToRefreshBase... pullToRefreshBase) {
        if (pullToRefreshBase != null) {
            for (PullToRefreshBase refreshBase : pullToRefreshBase) {
                refreshBase.setOnRefreshListener(listener2);
                refreshBase.onRefreshComplete();
                refreshBase.setMode(refreshMode);
            }
        } else {
            showToast("请实例化对象");
        }
    }

    /**
     * 数据加载完调用
     *
     * @param refreshMode 刷新完成后设置下拉上啦模式
     */
    protected void onLoad(final PullToRefreshBase.Mode refreshMode, PullToRefreshBase... pullToRefreshBase) {
        if (pullToRefreshBase != null) {
            for (final PullToRefreshBase refreshBase : pullToRefreshBase) {
                try {
                    refreshBase.onRefreshComplete();
                    refreshBase.setMode(refreshMode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                /**
//                 * 延时取消加载（Pull本身一些奇怪现象）
//                 */
//                refreshBase.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //hj add - 有时候界面destroy，refreshBase已经销毁，空指针
//                        try {
//                            refreshBase.onRefreshComplete();
//                            refreshBase.setMode(refreshMode);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 500);
            }
        } else {
            showToast("请实例化对象");
        }
    }

    /**
     * 下拉刷新
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {}

    /**
     * 上拉刷新
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {}

    @Override
    public void initIntentAndMemData() {}

    @Override
    public void doInitBaseHttp() {}

    @Override
    public void onViewClick(View v) {}

    @Override
    public void initWidget(View... v) {}

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
        showToast(e.toString().replace("java.io.IOException: ",""));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        cancelLoading();
    }

    @Override
    public void onObjectResponse(Object data, Response response, int id) {

    }
}
