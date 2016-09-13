package com.atman.wysq.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.event.YunXinAuthOutEvent;
import com.atman.wysq.model.request.LoginRequestModel;
import com.atman.wysq.model.response.GetChatTokenModel;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.model.response.LoginResultModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.CreateGestrureLockActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcher;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述 登录
 * 作者 tangbingliang
 * 时间 16/7/5 09:49
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class LoginActivity extends MyBaseActivity implements EditCheckBack {

    @Bind(R.id.login_username_et)
    MyCleanEditText loginUsernameEt;
    @Bind(R.id.login_password_et)
    MyCleanEditText loginPasswordEt;
    @Bind(R.id.login_sumbit_bt)
    Button loginSumbitBt;

    private Context mContext = LoginActivity.this;
    private String userName = "";
    private String passWord = "";

    private TextView barRightTx;

    private AbortableFuture<LoginInfo> loginRequest;
    private LoginResultModel mLoginResultModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        disableLoginCheck();
    }

    /**
     * 获取Intent
     *
     * @param context
     * @param successIntent 登陆成功后跳转的activity 的Intent
     * @return
     */
    public static Intent createIntent(Context context, Intent successIntent) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (successIntent != null) {
            intent.putExtra("successIntent", successIntent);
        }
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleIv(R.mipmap.top_login_ic);
        barRightTx = setBarRightTx("免费注册");
        barRightTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, RegisterActivity.class), Common.toRegister);
            }
        });
        loginUsernameEt.addTextChangedListener(new MyTextWatcher(mContext,loginUsernameEt,false,11,"",this));
        loginPasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName = PreferenceUtil.getPreferences(mContext,PreferenceUtil.PARM_US);
        if (!userName.isEmpty()) {
            loginUsernameEt.setText(userName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toRegister || requestCode == Common.toLoginCreateGesrure) {
//            LogUtils.e("Common.toRegister:"+Common.toRegister);
            LoginRequestModel mLoginRequestModel = new LoginRequestModel(PreferenceUtil.getPreferences(mContext,PreferenceUtil.PARM_US)
                    ,PreferenceUtil.getPreferences(mContext,PreferenceUtil.PARM_PW)
                    ,MyBaseApplication.mPhoneDeviceId
                    ,MyBaseApplication.mDeviceToken,false
                    ,MyBaseApplication.mVersionName
                    ,MyBaseApplication.mPhoneModel
                    ,MyBaseApplication.mChannel);
            OkHttpUtils.postString()
                    .url(Common.Url_Login).tag(LoginActivity.this).id(Common.NET_LOGIN_ID)
                    .content(mGson.toJson(mLoginRequestModel))
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                    .execute(new MyStringCallback(mContext, this, true));
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LOGIN_ID) {
            mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);

            OkHttpUtils.get().url(Common.Url_Get_ChatToken)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_CHATTOKEN).id(Common.NET_GET_CHATTOKEN).build()
                    .execute(new MyStringCallback(mContext, this, true));
        } else if (id == Common.NET_GET_USERINDEX) {
            GetUserIndexModel mGetUserIndexModel = mGson.fromJson(data, GetUserIndexModel.class);
            MyBaseApplication.mGetUserIndexModel = mGetUserIndexModel;
            MyBaseApplication.getApplication().initObserver();
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        } else if (id == Common.NET_GET_CHATTOKEN) {
            showLoading();
            GetChatTokenModel mGetChatTokenModel = mGson.fromJson(data, GetChatTokenModel.class);
            PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_YUNXIN_TOKEN, mGetChatTokenModel.getBody());
            final String account = mLoginResultModel.getBody()+"";
            final String token = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_YUNXIN_TOKEN);

            // 登录
            loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
            loginRequest.setCallback(new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    cancelLoading();

                    PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_PW,MD5Util.getMD5(passWord));
                    PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_USERID,mLoginResultModel.getBody()+"");
                    MyBaseApplication.getApplication().setLock(false);
                    if (PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_GESTURE_PW).isEmpty()
                            && PreferenceUtil.getBoolPreferences(LoginActivity.this, PreferenceUtil.PARM_ISOPEN_GESTURE)) {
                        startActivityForResult(new Intent(mContext, CreateGestrureLockActivity.class), Common.toLoginCreateGesrure);
                    } else {
                        OkHttpUtils.get().url(Common.Url_Get_UserIndex +"/"+ mLoginResultModel.getBody())
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                                .execute(new MyStringCallback(mContext, LoginActivity.this, true));
                    }
                }

                @Override
                public void onFailed(int code) {
                    cancelLoading();
                    if (code == 302 || code == 404) {
                        showToast("帐号或密码错误");
                    } else {
                        showToast("登录失败" + code);
                    }
                    MyBaseApplication.getApplication().cleanLoginData();
                    EventBus.getDefault().post(new YunXinAuthOutEvent());
                }

                @Override
                public void onException(Throwable exception) {
                    cancelLoading();
                    showToast("无效输入");
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
    }

    @OnClick({R.id.login_sumbit_bt, R.id.login_forgetPW_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_sumbit_bt:
                if (checkParms()) {
                    return;
                }
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_US,userName);
                LoginRequestModel mLoginRequestModel = new LoginRequestModel(userName, MD5Util.getMD5(passWord)
                        ,MyBaseApplication.mPhoneDeviceId
                        ,MyBaseApplication.mDeviceToken,false
                        ,MyBaseApplication.mVersionName
                        ,MyBaseApplication.mPhoneModel
                        ,MyBaseApplication.mChannel);
//                LogUtils.e("mGson.toJson(mLoginRequestModel):"+mGson.toJson(mLoginRequestModel));
                OkHttpUtils.postString()
                        .url(Common.Url_Login).tag(LoginActivity.this).id(Common.NET_LOGIN_ID)
                        .content(mGson.toJson(mLoginRequestModel))
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.login_forgetPW_tx:
                startActivity(new Intent(mContext, NewPassWordActivity.class));
                break;
        }
    }

    private boolean checkParms() {
        userName = loginUsernameEt.getText().toString().trim();
        passWord = loginPasswordEt.getText().toString().trim();
        if (!StringUtils.isPhone(userName)){
            showToast("请输入正确的手机号");
            return true;
        } else if (passWord.isEmpty() || passWord.length()<6) {
            showToast("密码长度为 6-16位");
            return true;
        }
        return false;
    }

    @Override
    public void isNull() {
        if (loginUsernameEt.getText().toString().trim().isEmpty()
                || loginPasswordEt.getText().toString().trim().isEmpty()) {
            loginSumbitBt.setEnabled(false);
        } else {
            if (loginUsernameEt.getText().toString().trim().length()>0
                    && loginPasswordEt.getText().toString().trim().length()>=0) {
                loginSumbitBt.setEnabled(true);
            } else {
                loginSumbitBt.setEnabled(false);
            }
        }
    }
}
