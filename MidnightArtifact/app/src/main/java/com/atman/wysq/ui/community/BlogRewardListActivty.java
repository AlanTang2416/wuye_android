package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.RewardListViewAdapter;
import com.atman.wysq.model.response.GetRewardListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/5 11:55
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BlogRewardListActivty extends MyBaseActivity {

    @Bind(R.id.bloglist_lv)
    ListView bloglistLv;

    private Context mContext = BlogRewardListActivty.this;
    private int blogId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activty_blogrewardlist);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int blogId) {
        Intent intent = new Intent(context, BlogRewardListActivty.class);
        intent.putExtra("blogId", blogId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        blogId = getIntent().getIntExtra("blogId", -1);

        setBarTitleTx("所有献花");
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Award_List + blogId).id(Common.NET_GET_AWARDLIST)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_AWARDLIST).build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_AWARDLIST) {
            GetRewardListModel mGetRewardListModel = mGson.fromJson(data, GetRewardListModel.class);
            RewardListViewAdapter mRewardListViewAdapter = new RewardListViewAdapter(mContext, mGetRewardListModel.getBody());
            bloglistLv.setAdapter(mRewardListViewAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_AWARDLIST);
    }
}
