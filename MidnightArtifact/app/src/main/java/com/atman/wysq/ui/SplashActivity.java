package com.atman.wysq.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetGoldenRoleModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.downfile.DownloadFile;
import com.base.baselibs.iimp.TimeCountInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.TimeCount;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by loyee on 16-1-5.
 */
public class SplashActivity extends MyBaseActivity implements TimeCountInterface {
    @Bind(R.id.splash_iv_one)
    ImageView splashIvOne;
    @Bind(R.id.splash_iv_two)
    ImageView splashIvTwo;
    @Bind(R.id.jump_tx)
    TextView jumpTx;

    private boolean isInit = true;
    private TimeCount timeCount;
    private Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        timeCount = new TimeCount(5 * 1000, 1000, this);

        getSomedate();
    }

    private void getSomedate() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float scaledDensity = metric.scaledDensity;
        LogUtils.e("width:"+width+",height:"+height+",density:"+density+",densityDpi:"+densityDpi+",scaledDensity:"+scaledDensity);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
    }

    private void redirectTo() {
        if (isInit) {
            isInit = false;
            boolean isLearned = PreferenceUtil.getBoolPreferences(this, "isLearned");
            if (!isLearned) {
                goGuide();
            } else {
                goHome();
            }
        }
    }

    private void goHome() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void goGuide() {
//        startActivity(new Intent(this, GuideActivity.class));
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String us = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_US);
        String pw = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_PW);
        new DownloadFile(SplashActivity.this, splashIvOne, splashIvTwo)
                .execute("http://www.5ys7.com/url_resource.json");
        timeCount.start();

        OkHttpUtils.get().url(Common.Url_Get_GoldenRole)
                .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_GOLDENROLE).id(Common.NET_GET_GOLDENROLE).build()
                .execute(new MyStringCallback(mContext, this, false));
    }

    @OnClick(R.id.jump_tx)
    public void onClick() {
        timeCount.cancel();
        toMainActivity();
    }

    private void toMainActivity() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id==Common.NET_GET_GOLDENROLE) {
            MyBaseApplication.mGetGoldenRoleModel = mGson.fromJson(data, GetGoldenRoleModel.class);
        }
    }

    @Override
    public void onTimeOut() {
        toMainActivity();
    }

    @Override
    public void onBackTick(long millisUntilFinished) {
        jumpTx.setText("跳过 " + millisUntilFinished / 1000 + " s");
    }

    @OnClick({R.id.splash_iv_one, R.id.splash_iv_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash_iv_one:
            case R.id.splash_iv_two:
                timeCount.cancel();
                startActivity(MainActivity.buildIntent(SplashActivity.this,true));
                finish();
                break;
        }
    }
}
