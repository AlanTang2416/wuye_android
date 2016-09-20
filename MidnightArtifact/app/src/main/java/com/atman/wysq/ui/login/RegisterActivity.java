package com.atman.wysq.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.RegisterRequestModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcher;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 注册
 * 作者 tangbingliang
 * 时间 16/7/6 09:33
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class RegisterActivity extends MyBaseActivity implements EditCheckBack {

    @Bind(R.id.register_username_et)
    MyCleanEditText registerUsernameEt;
    @Bind(R.id.register_password_et)
    MyCleanEditText registerPasswordEt;
    @Bind(R.id.register_code_et)
    MyCleanEditText registerCodeEt;
    @Bind(R.id.register_code_bt)
    TextView registerCodeBt;
    @Bind(R.id.register_man_rt)
    RadioButton registerManRt;
    @Bind(R.id.register_weman_rt)
    RadioButton registerWemanRt;
    @Bind(R.id.register_gender_rg)
    RadioGroup registerGenderRg;
    @Bind(R.id.register_sumbit_bt)
    Button registerSumbitBt;

    private Context mContext = RegisterActivity.this;
    private TimeCount timeCount;
    private String mPhoneNumber;
    private String mPassWord;
    private String mCode;
    private String mGender = "M";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        disableLoginCheck();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleIv(R.mipmap.top_register_ic);
        timeCount = new TimeCount(registerCodeBt, 2 * 60 * 1000, 1000, registerUsernameEt);

        registerUsernameEt.addTextChangedListener(new MyTextWatcher(mContext,registerUsernameEt,false,11,"",this));
        registerPasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
        registerCodeEt.addTextChangedListener(new MyTextWatcher(mContext,registerCodeEt,false,6,"",this,true));

        //挨着给每个RadioButton加入drawable限制边距以控制显示大小
        RadioButton[] bt = {registerManRt, registerWemanRt};
        for (int i=0;i<bt.length;i++) {
            Drawable[] drs = bt[i].getCompoundDrawables();
            Rect r = new Rect(0, 0, drs[1].getMinimumWidth() + 30, drs[1].getMinimumHeight() + 30);
            drs[1].setBounds(r);
            bt[i].setCompoundDrawables(null,drs[1],null,null);
        }

        registerGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.register_man_rt:
                        mGender = "M";
                        break;
                    case R.id.register_weman_rt:
                        mGender = "F";
                        break;
                }
            }
        });
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SMS_ID) {
            showToast("验证码发送成功，请注意查收！");
            timeCount.start();
        } else if (id == Common.NET_REGISTER_ID) {
//            LogUtils.e(">>>>>>>>>Common.NET_REGISTER_ID");
            PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_PW,MD5Util.getMD5(mPassWord));
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SMS_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_REGISTER_ID);
    }

    @OnClick({R.id.register_code_bt, R.id.register_sumbit_bt, R.id.register_agreement_tx})
    public void onClick(View view) {
        mPhoneNumber = registerUsernameEt.getText().toString().trim();
        mPassWord = registerPasswordEt.getText().toString().trim();
        mCode = registerCodeEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.register_code_bt:
                if (checkParms(1)) {
                    return;
                }
                OkHttpUtils.post().url(Common.Url_SeedCode+mPhoneNumber+"/1")
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_SMS_ID).id(Common.NET_SMS_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.register_sumbit_bt:
                if (checkParms(2)) {
                    return;
                }
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_US,mPhoneNumber);
                RegisterRequestModel mRegisterRequestModel = new RegisterRequestModel(mPhoneNumber,
                        MD5Util.getMD5(mPassWord), mCode, mPhoneNumber, mGender,
                        MyBaseApplication.mPhoneMac, MyBaseApplication.mPhoneDeviceId,
                        MyBaseApplication.mPhoneDeviceId, MyBaseApplication.mVersionName);
                OkHttpUtils.postString().url(Common.Url_Register).content(mGson.toJson(mRegisterRequestModel)).mediaType(Common.JSON)
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_REGISTER_ID).id(Common.NET_REGISTER_ID)
                        .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.register_agreement_tx:
                startActivity(new Intent(mContext, UserAgreementActivity.class));
                break;
        }
    }

    private boolean checkParms(int num) {
        if (mPhoneNumber.isEmpty()) {
            showToast("请输入手机号");
            return true;
        } else if (!StringUtils.isPhone(mPhoneNumber)) {
            showToast("请输入正确的手机号");
            return true;
        } else if ((mPassWord.isEmpty() || mPassWord.length()<6) && num==2) {
            showToast("密码长度为 6-16位");
            return true;
        } else if ((registerCodeEt.getText().toString().trim().isEmpty()
                || registerCodeEt.getText().toString().trim().length()<6) && num==2) {
            showToast("验证码输入错误");
            return true;
        }
        return false;
    }

    @Override
    public void isNull() {
        if (registerUsernameEt.getText().toString().trim().isEmpty()
                || registerPasswordEt.getText().toString().trim().isEmpty()
                || registerCodeEt.getText().toString().trim().isEmpty()) {
            registerSumbitBt.setEnabled(false);
        } else {
            registerSumbitBt.setEnabled(true);
//            if (registerUsernameEt.getText().toString().trim().length()==11
//                    && registerPasswordEt.getText().toString().trim().length()>=0
//                    && registerCodeEt.getText().toString().trim().length()==6) {
//                registerSumbitBt.setEnabled(true);
//            } else {
//                registerSumbitBt.setEnabled(false);
//            }
        }
    }
}
