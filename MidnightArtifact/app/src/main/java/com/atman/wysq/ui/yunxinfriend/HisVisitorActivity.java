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
import com.atman.wysq.adapter.GetHisVisitorAdapter;
import com.atman.wysq.model.response.GetUserBrowseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/29 12:03
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class HisVisitorActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.hisdvisitor_totop_iv)
    ImageView hisdvisitorTotopIv;

    private Context mContext = HisVisitorActivity.this;

    private View headView;
    private TextView headViewNumTx;
    private int mPage = 1;
    private long userId;
    private GetUserBrowseModel mGetUserBrowseModel;

    private GetHisVisitorAdapter mAdapter;
    private View mEmpty;
    private TextView mEmptyTX;
    private String title;
    private int num;
    private List<GetUserBrowseModel.BodyEntity.DataListEntity> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisvisitor);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long id, String title, int num){
        Intent intent = new Intent(context, HisVisitorActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("num", num);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        userId = getIntent().getLongExtra("id", 0);
        title = getIntent().getStringExtra("title");
        num = getIntent().getIntExtra("num", 0);
        setBarTitleTx(title);

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_visitor_head_view, null);
        headViewNumTx = (TextView) headView.findViewById(R.id.othervisitor_num_tx);
        headViewNumTx.setText(""+num);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无访客");

        mAdapter = new GetHisVisitorAdapter(mContext, getmWidth(), this);
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
                if (firstVisibleItem >= 5 && totalItemCount!=0) {
                    hisdvisitorTotopIv.setVisibility(View.VISIBLE);
                } else {
                    hisdvisitorTotopIv.setVisibility(View.GONE);
                }
            }
        });
        hisdvisitorTotopIv.setVisibility(View.GONE);
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
        OkHttpUtils.get().url(Common.Url_Get_Browse + userId + "/" + mPage).id(Common.NET_GET_BROWSE)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BROWSE).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_BROWSE) {
            mGetUserBrowseModel = mGson.fromJson(data, GetUserBrowseModel.class);
            if (mGetUserBrowseModel.getBody().getDataList() == null || mGetUserBrowseModel.getBody().getDataList().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                dataList.clear();
                for (int i=0;i<mGetUserBrowseModel.getBody().getDataList().size();i++) {
                    if (mGetUserBrowseModel.getBody().getDataList().get(i).getUser_id() != userId) {
                        dataList.add(mGetUserBrowseModel.getBody().getDataList().get(i));
                    }
                }
                mAdapter.addBody(dataList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BROWSE);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getItem(position).getUser_id() == MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserId()) {
            showWraning("亲，这是你自己哦！");
            return;
        }
        startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
    }

    @OnClick({R.id.hisdvisitor_totop_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hisdvisitor_totop_iv:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
        }
    }
}
