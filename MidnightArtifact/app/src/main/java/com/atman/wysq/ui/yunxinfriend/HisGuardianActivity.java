package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.GetHisGuardianAdapter;
import com.atman.wysq.model.response.GetHisGuardModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/29 16:20
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class HisGuardianActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.hisdguardian_totop_iv)
    ImageView hisdguardianTotopIv;
    private Context mContext = HisGuardianActivity.this;

    private long id = -1;
    private View headView;
    private TextView headViewNumTx;
    private int mPage = 1;
    private View mEmpty;
    private TextView mEmptyTX;
    private GetHisGuardianAdapter mAdapter;
    private GetHisGuardModel mGetHisGuardModel;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisguardian);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long id, String title) {
        Intent intent = new Intent(context, HisGuardianActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        id = getIntent().getLongExtra("id", -1);
        title = getIntent().getStringExtra("title");
        setBarTitleTx(title);
//        id = 450000168;

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_guardian_head_view, null);
        headViewNumTx = (TextView) headView.findViewById(R.id.otherguardian_num_tx);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无守护者");

        mAdapter = new GetHisGuardianAdapter(mContext, getmWidth(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.getRefreshableView().addHeaderView(headView);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        pullToRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 5 && totalItemCount != 0) {
                    hisdguardianTotopIv.setVisibility(View.VISIBLE);
                } else {
                    hisdguardianTotopIv.setVisibility(View.GONE);
                }
            }
        });
        hisdguardianTotopIv.setVisibility(View.GONE);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        mPage = 1;
        dohttp(true);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        dohttp(false);
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_Guard + id + "/" + mPage).id(Common.NET_GET_GUARD)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_GUARD).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GUARD) {
            mGetHisGuardModel = mGson.fromJson(data, GetHisGuardModel.class);
            headViewNumTx.setText(""+mGetHisGuardModel.getBody().getDataSize());
            if (mGetHisGuardModel.getBody().getDataList() == null || mGetHisGuardModel.getBody().getDataList().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addBody(mGetHisGuardModel.getBody().getDataList());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GUARD);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getItem(position).getUser_id() == MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserId()) {
            showWraning("亲，这是你自己哦！");
            return;
        }
        startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
    }

    @OnClick({R.id.hisdguardian_totop_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hisdguardian_totop_iv:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
        }
    }
}
