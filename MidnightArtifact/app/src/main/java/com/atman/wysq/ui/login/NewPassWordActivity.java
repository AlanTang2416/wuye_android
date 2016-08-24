package com.atman.wysq.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.NewPassWordRequestModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcher;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 密码找回
 * 作者 tangbingliang
 * 时间 16/7/6 09:34
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class NewPassWordActivity extends MyBaseActivity implements EditCheckBack {

    @Bind(R.id.newpassword_username_et)
    MyCleanEditText newpasswordUsernameEt;
    @Bind(R.id.newpassword_newpassword_et)
    MyCleanEditText newpasswordNewpasswordEt;
    @Bind(R.id.newpassword_code_et)
    MyCleanEditText newpasswordCodeEt;
    @Bind(R.id.newpassword_code_bt)
    TextView newpasswordCodeBt;
    @Bind(R.id.newpassword_sumbit_bt)
    Button newpasswordSumbitBt;

    private Context mContext = NewPassWordActivity.this;
    private TimeCount timeCount;
    private String mPhoneNumber;
    private String mPassWord;
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_newpassword);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleIv(R.mipmap.top_newpasswd_ic);
        timeCount = new TimeCount(newpasswordCodeBt, 2 * 60 * 1000, 1000, newpasswordUsernameEt, newpasswordNewpasswordEt);

        newpasswordUsernameEt.addTextChangedListener(new MyTextWatcher(mContext,newpasswordUsernameEt,false,11,"",this));
        newpasswordNewpasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
        newpasswordCodeEt.addTextChangedListener(new MyTextWatcher(mContext,newpasswordCodeEt,false,6,"",this,true));
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
        } else if (id == Common.NET_NEWPW) {
            PromptDialog.Builder builder = new PromptDialog.Builder(NewPassWordActivity.this);
            builder.setCancelable(false);
            builder.setMessage("密码修改成功，请重新登录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        timeCount.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SMS_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_NEWPW);
    }

    @OnClick({R.id.newpassword_code_bt, R.id.newpassword_sumbit_bt})
    public void onClick(View view) {
        mPhoneNumber = newpasswordUsernameEt.getText().toString().trim();
        mPassWord = newpasswordNewpasswordEt.getText().toString().trim();
        mCode = newpasswordCodeEt.getText().toString().trim();
        if (checkParms()) {
            return;
        }
        switch (view.getId()) {
            case R.id.newpassword_code_bt:
                OkHttpUtils.post().url(Common.Url_SeedCode+mPhoneNumber+"/2")
                        .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                        .tag(Common.NET_SMS_ID).id(Common.NET_SMS_ID).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.newpassword_sumbit_bt:
                OkHttpUtils.postString().url(Common.Url_NewPW)
                        .content(mGson.toJson(new NewPassWordRequestModel(mPhoneNumber, MD5Util.getMD5(mPassWord), mCode)))
                        .mediaType(Common.JSON).tag(Common.NET_NEWPW).id(Common.NET_NEWPW)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                        .execute(new MyStringCallback(mContext, this, true));
                break;
        }
    }

    private boolean checkParms() {
        if (mPhoneNumber.isEmpty()) {
            showToast("请输入手机号");
            return true;
        } else if (!StringUtils.isPhone(mPhoneNumber)) {
            showToast("请输入正确的手机号");
            return true;
        } else if (mPassWord.isEmpty() || mPassWord.length()<6) {
            showToast("密码长度为 6-16位");
            return true;
        }
        return false;
    }

    @Override
    public void isNull() {
        if (newpasswordUsernameEt.getText().toString().trim().isEmpty()
                || newpasswordNewpasswordEt.getText().toString().trim().isEmpty()
                || newpasswordCodeEt.getText().toString().trim().isEmpty()) {
            newpasswordSumbitBt.setEnabled(false);
        } else {
            if (newpasswordUsernameEt.getText().toString().trim().length()==11
                    && newpasswordNewpasswordEt.getText().toString().trim().length()>=0
                    && newpasswordCodeEt.getText().toString().trim().length()==6) {
                newpasswordSumbitBt.setEnabled(true);
            } else {
                newpasswordSumbitBt.setEnabled(false);
            }
        }
    }
}
