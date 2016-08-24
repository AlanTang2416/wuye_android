package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.request.ReportModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/1 09:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ReportActivity extends MyBaseActivity {

    @Bind(R.id.report_content_et)
    MyCleanEditText reportContentEt;

    private RelativeLayout mRelativeLayout;

    private Context mContext = ReportActivity.this;

    private int id;
    private String str = "";

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mRelativeLayout.setClickable(true);
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int id) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        id = getIntent().getIntExtra("id", -1);

        setBarTitleTx("举报");
        setBarRightIv(R.mipmap.bt_create_ok);
        mRelativeLayout = getBarRightRl();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dohttp();
            }
        });
    }

    private void dohttp() {
        str = reportContentEt.getText().toString().trim();
        if (str.isEmpty()) {
            showToast("请输入举报内容");
            return;
        }
        Timer tExit = new Timer();
        tExit.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 2000);
        mRelativeLayout.setClickable(false);
        ReportModel mReportModel = new ReportModel(str, 1, id);
        OkHttpUtils.postString().url(Common.Url_RePort).mediaType(Common.JSON)
                .content(mGson.toJson(mReportModel))
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .id(Common.NET_REPORT).tag(Common.NET_REPORT).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_REPORT) {
            showToast("举报成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_REPORT);
    }
}
