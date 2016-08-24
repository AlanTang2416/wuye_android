package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcher;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DataCleanManager;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/8 15:09
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ModifyNickAcitvity extends MyBaseActivity implements EditCheckBack {

    @Bind(R.id.modifynick_new_et)
    MyCleanEditText modifynickNewEt;
    @Bind(R.id.modifynick_ok_bt)
    Button modifynickOkBt;

    private Context mContext = ModifyNickAcitvity.this;
    private int nameChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_modifynick);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int nameChange) {
        Intent intent = new Intent(context, ModifyNickAcitvity.class);
        intent.putExtra("nameChange", nameChange);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("修改昵称");
        modifynickNewEt.addTextChangedListener(new MyTextWatcherTwo(this));
        nameChange = getIntent().getIntExtra("nameChange", 0);
        if (nameChange==1) {
            int CostGolden = MyBaseApplication.mGetGoldenRoleModel.getBody().get("8").getCost_golden();

            PromptDialog.Builder builder = new PromptDialog.Builder(ModifyNickAcitvity.this);
            builder.setMessage("您的一次免费修改机会已使用，本次修改将消耗"+CostGolden+"金币");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_RESET_NICK) {
            showToast("昵称修改成功");
            Intent mIntent = new Intent();
            setResult(RESULT_OK,mIntent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.modifynick_ok_bt)
    public void onClick() {
        String str = "";
        if (nameChange==1) {
            str = "修改昵称将花费"+MyBaseApplication.mGetGoldenRoleModel.getBody().get("8").getCost_golden()+"金币，确定修改吗？";
        } else {
            str = "亲，您只有一次免费修改昵称的机会，确定要修改吗？";
        }
        PromptDialog.Builder builder = new PromptDialog.Builder(ModifyNickAcitvity.this);
        builder.setTitle("提示");
        builder.setMessage(str);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = modifynickNewEt.getText().toString().trim();
                OkHttpUtils.postString().url(Common.Url_Reset_Nick+str).tag(Common.NET_RESET_NICK).content("{nick}")
                        .mediaType(Common.JSON).id(Common.NET_RESET_NICK).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                        .build().execute(new MyStringCallback(mContext, ModifyNickAcitvity.this, true));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void isNull() {
        if (modifynickNewEt.getText().toString().trim().isEmpty()) {
            modifynickOkBt.setEnabled(false);
        } else {
            modifynickOkBt.setEnabled(true);
        }
    }
}
