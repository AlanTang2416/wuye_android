package com.atman.wysq.wxapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/23 17:25
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class WXPayEntryActivity extends MyBaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        hideTitleBar();
        api = WXAPIFactory.createWXAPI(this, MyBaseApplication.appId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String errorString;
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                errorString = "支付成功";
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                errorString = "取消支付";
            } else {
                errorString = "支付失败";
            }
            PromptDialog.Builder builder = new PromptDialog.Builder(WXPayEntryActivity.this);
            builder.setTitle("提示");
            builder.setMessage(errorString);
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
}
