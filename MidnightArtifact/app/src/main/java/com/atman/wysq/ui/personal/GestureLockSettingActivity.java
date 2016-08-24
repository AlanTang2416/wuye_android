package com.atman.wysq.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.switchbutton.SwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/11 10:40
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GestureLockSettingActivity extends MyBaseActivity {

    @Bind(R.id.setting_reset_gesture_pw_ll)
    LinearLayout settingResetGesturePwLl;
    @Bind(R.id.setting_reset_gesture_pw_top_iv)
    ImageView settingResetGesturePwTopIv;
    @Bind(R.id.setting_reset_gesture_pw_bottom_iv)
    ImageView settingResetGesturePwBottomIv;
    @Bind(R.id.setting_open_sb)
    SwitchButton settingOpenSb;

    private Context mContext = GestureLockSettingActivity.this;
    private String mGesturePW = "";
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturelocksetting);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("手势锁");

        settingOpenSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mGesturePW == "" || mGesturePW.isEmpty()) {
                        startActivityForResult(new Intent(mContext, CreateGestrureLockActivity.class), Common.toCreateGesrure);
                    } else {
                        PreferenceUtil.saveBoolPreference(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE, true);
                    }
                } else {
                    PreferenceUtil.saveBoolPreference(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE, false);
                }
                hitView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        hitView();
    }

    private void hitView() {
        mGesturePW = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_GESTURE_PW);
        isOpen = PreferenceUtil.getBoolPreferences(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE);
        if (mGesturePW == "" || mGesturePW.isEmpty()) {
            //没有手势密码,或者有手势密码但是没开
            settingOpenSb.setCheckedImmediately(false);
            settingResetGesturePwLl.setVisibility(View.INVISIBLE);
            settingResetGesturePwTopIv.setVisibility(View.INVISIBLE);
            settingResetGesturePwBottomIv.setVisibility(View.INVISIBLE);
        } else {
            settingOpenSb.setCheckedImmediately(isOpen);
            if (isOpen) {
                settingResetGesturePwLl.setVisibility(View.VISIBLE);
                settingResetGesturePwTopIv.setVisibility(View.VISIBLE);
                settingResetGesturePwBottomIv.setVisibility(View.VISIBLE);
            } else {
                settingResetGesturePwLl.setVisibility(View.INVISIBLE);
                settingResetGesturePwTopIv.setVisibility(View.INVISIBLE);
                settingResetGesturePwBottomIv.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.setting_reset_gesture_pw_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_reset_gesture_pw_ll:
                startActivityForResult(new Intent(mContext, CreateGestrureLockActivity.class), Common.toCreateGesrure);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toCreateGesrure) {
            if (data.getBooleanExtra("isOk", false)) {
                showToast("设置成功");
            }
        }
    }
}
