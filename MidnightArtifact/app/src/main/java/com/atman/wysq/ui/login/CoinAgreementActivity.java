package com.atman.wysq.ui.login;

import android.os.Bundle;
import android.view.View;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;

/**
 * 描述 用户协议
 * 作者 tangbingliang
 * 时间 16/7/6 16:17
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CoinAgreementActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_coinagreement);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("午夜神器用户协议");
    }
}
