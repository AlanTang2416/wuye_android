package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.LockPatternView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/11 10:49
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CreateGestrureLockActivity extends MyBaseActivity implements
        LockPatternView.OnPatternListener {

    @Bind(R.id.create_lock_tx)
    TextView createLockTx;
    @Bind(R.id.create_lock_lpv)
    LockPatternView createLockLpv;

    private Context mContext = CreateGestrureLockActivity.this;

    private static final int STEP_1 = 1; // 开始
    private static final int STEP_2 = 2; // 第一次设置手势完成，准备第二次
    private static final int STEP_3 = 3; // 两次密码不一致
    private static final int STEP_4 = 4; // 第二次设置手势完成

    private int step;

    private List<LockPatternView.Cell> choosePattern;
    private boolean confirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_creategestrurelock);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("手势密码");

        setSwipeBackEnable(false);
        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        createLockLpv.setOnPatternListener(this);
        step = STEP_1;
        updateView();
    }

    private void updateView() {
        switch (step) {
            case STEP_1:
                choosePattern = null;
                confirm = false;
                createLockLpv.clearPattern();
                createLockLpv.enableInput();
                break;
            case STEP_2:
                createLockTx.setText("请再次绘制解锁图案");
                createLockLpv.clearPattern();
                createLockLpv.enableInput();
                break;
            case STEP_3:
                createLockTx.setText("与上一次输入不一致，请重新设置");
                choosePattern = null;
                confirm = false;
                step = STEP_1;
                createLockLpv.clearPattern();
                createLockLpv.enableInput();
                break;
            case STEP_4:
                if (confirm) {
                    createLockLpv.disableInput();
                    PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_GESTURE_PW, LockPatternView.patternToString(choosePattern));
                    PreferenceUtil.saveBoolPreference(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE, true);
                    PreferenceUtil.saveIntPreference(mContext, PreferenceUtil.PARM_GESTURE_ERROR, 4);
                    MyBaseApplication.getApplication().setLock(false);
                    Intent mIntent = new Intent();
                    mIntent.putExtra("isOk", true);
                    setResult(RESULT_OK,mIntent);
                    finish();
                } else {
                    createLockLpv.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    createLockLpv.enableInput();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            showToast("至少连接4个点，请重试");
            createLockLpv.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        if (choosePattern == null) {
            choosePattern = new ArrayList<LockPatternView.Cell>(pattern);
            LogUtils.e("choosePattern = "+ Arrays.toString(choosePattern.toArray()));
            step = STEP_2;
            updateView();
            return;
        }

        LogUtils.e("choosePattern = "+Arrays.toString(choosePattern.toArray()));
        LogUtils.e("pattern = "+Arrays.toString(pattern.toArray()));

        if (choosePattern.equals(pattern)) {
            LogUtils.e("pattern = "+Arrays.toString(pattern.toArray()));
            step = STEP_4;
            confirm = true;
        } else {
            step = STEP_3;
            confirm = false;
        }
        updateView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_GESTURE_PW, "");
        PreferenceUtil.saveBoolPreference(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE, false);
        Intent mIntent = new Intent();
        mIntent.putExtra("isOk", false);
        setResult(RESULT_OK,mIntent);
        finish();
    }
}
