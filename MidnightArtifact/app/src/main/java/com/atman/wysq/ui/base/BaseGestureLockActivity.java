package com.atman.wysq.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.TimeCountInterface;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.LockPatternView;
import com.base.baselibs.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/11 15:38
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseGestureLockActivity extends MyBaseActivity implements
        LockPatternView.OnPatternListener,TimeCountInterface {

    @Bind(R.id.base_gesturelock_head_iv)
    RoundImageView baseGesturelockHeadIv;
    @Bind(R.id.base_gesturelock_tx)
    TextView baseGesturelockTx;
    @Bind(R.id.base_gesturelock_lpv)
    LockPatternView baseGesturelockLpv;

    private Context mContext = BaseGestureLockActivity.this;

    private static final int STEP_1 = 1; // 开始
    private static final int STEP_2 = 2; // 密码错误

    private int step;

    private List<LockPatternView.Cell> choosePattern;
    private boolean confirm = false;
    private String mGestureStr = "";
    private int mGestureError;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basegesturelock);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
        setSwipeBackEnable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        timeCount = new TimeCount(1000, 1000, this);

        mGestureStr = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_GESTURE_PW);
        mGestureError = PreferenceUtil.getIntPreferences(mContext, PreferenceUtil.PARM_GESTURE_ERROR);
        baseGesturelockLpv.setOnPatternListener(this);
        step = STEP_1;
        baseGesturelockTx.setText("请输入手势密码");
        baseGesturelockTx.setTextColor(getResources().getColor(R.color.color_white));
        updateView();

        ImageLoader.getInstance().displayImage(Common.ImageUrl + MyBaseApplication.mHEAD_URL
                , baseGesturelockHeadIv, MyBaseApplication.getApplication().getOptions());
    }

    private void updateView() {
        switch (step) {
            case STEP_1:
                choosePattern = null;
                confirm = false;
                baseGesturelockLpv.clearPattern();
                baseGesturelockLpv.enableInput();
                break;
            case STEP_2:
                baseGesturelockTx.setText("密码错误，还可以输入"+mGestureError+"次");
                baseGesturelockTx.setTextColor(getResources().getColor(R.color.color_red));
                baseGesturelockLpv.clearPattern();
                baseGesturelockLpv.enableInput();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPatternStart() {

    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        choosePattern = new ArrayList<LockPatternView.Cell>(pattern);
        if (LockPatternView.patternToString(choosePattern).equals(mGestureStr)) {
            PreferenceUtil.saveIntPreference(mContext, PreferenceUtil.PARM_GESTURE_ERROR, 4);
            MyBaseApplication.getApplication().setLock(false);
            finish();
        } else {
            baseGesturelockLpv.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            PreferenceUtil.saveIntPreference(mContext, PreferenceUtil.PARM_GESTURE_ERROR, (mGestureError -= 1));
            if (mGestureError <= 0) {
                overGesture();
            } else {
                step = STEP_2;
                updateView();
                timeCount.start();
            }
        }
    }

    private void overGesture() {
        clearData();
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_GESTURE_PW, "");
        MyBaseApplication.getApplication().setLock(false);
        MyBaseApplication.getApplication().setError(true);
        startActivity(LoginActivity.createIntent(this, getIntent()));
        finish();
    }

    @OnClick(R.id.base_gesturelock_forgot_pw_tv)
    public void onClick() {
        PreferenceUtil.saveBoolPreference(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE, false);
        overGesture();
    }

    @Override
    public void onTimeOut() {
        step = STEP_1;
        updateView();
    }

    @Override
    public void onBackTick(long millisUntilFinished) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
