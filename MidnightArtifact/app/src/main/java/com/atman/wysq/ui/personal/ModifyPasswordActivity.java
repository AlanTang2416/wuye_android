package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.atman.wysq.R;
import com.atman.wysq.model.request.ReSetPWRequestModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述 修改密码
 * 作者 tangbingliang
 * 时间 16/7/7 17:02
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ModifyPasswordActivity extends MyBaseActivity implements EditCheckBack {

    @Bind(R.id.reset_oldpassword_et)
    MyCleanEditText resetOldpasswordEt;
    @Bind(R.id.reset_newpassword_et)
    MyCleanEditText resetNewpasswordEt;
    @Bind(R.id.reset_repassword_et)
    MyCleanEditText resetRepasswordEt;
    @Bind(R.id.reset_ok_bt)
    Button resetOkBt;

    private Context mContext = ModifyPasswordActivity.this;

    private String oldPassWord;
    private String newPassWord;
    private String reNewPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypassword);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("修改密码");

        resetOldpasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
        resetNewpasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
        resetRepasswordEt.addTextChangedListener(new MyTextWatcherTwo(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_RESET_PW) {
            MyBaseApplication.getApplication().cleanLoginData();
            showToast("密码修改成功，请重新登录");
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.Url_Reset_PW);
    }

    @OnClick(R.id.reset_ok_bt)
    public void onClick() {
        oldPassWord = resetOldpasswordEt.getText().toString().trim();
        newPassWord = resetNewpasswordEt.getText().toString().trim();
        reNewPassWord = resetRepasswordEt.getText().toString().trim();
        if (!newPassWord.equals(reNewPassWord)) {
            showToast("重复新密码与输入的新密码不一致");
            return;
        }
        ReSetPWRequestModel mReSetPWRequestModel = new ReSetPWRequestModel(MD5Util.getMD5(reNewPassWord)
                , MD5Util.getMD5(oldPassWord));
        OkHttpUtils.postString().url(Common.Url_Reset_PW).tag(Common.NET_RESET_PW).addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .id(Common.NET_RESET_PW).content(mGson.toJson(mReSetPWRequestModel)).mediaType(Common.JSON).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    public void isNull() {
        if (resetOldpasswordEt.getText().toString().trim().isEmpty()
                || resetNewpasswordEt.getText().toString().trim().isEmpty()
                || resetRepasswordEt.getText().toString().trim().isEmpty()) {
            resetOkBt.setEnabled(false);
        } else {
            if (resetOldpasswordEt.getText().toString().trim().length()>=6
                    && resetNewpasswordEt.getText().toString().trim().length()>=6
                    && resetRepasswordEt.getText().toString().trim().length()>=6) {
                resetOkBt.setEnabled(true);
            } else {
                resetOkBt.setEnabled(false);
            }
        }
    }
}
