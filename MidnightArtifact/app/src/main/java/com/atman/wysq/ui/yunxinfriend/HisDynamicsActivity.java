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
import com.atman.wysq.adapter.PostingListAdapter;
import com.atman.wysq.model.response.GetBolgListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.community.PostingsDetailActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
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
 * 时间 16/8/29 09:40
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class HisDynamicsActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.hisdynamics_totop_iv)
    ImageView hisdynamicsTotopIv;

    private Context mContext = HisDynamicsActivity.this;

    private View mEmpty;
    private TextView mEmptyTX;

    private PostingListAdapter mAdapter;
    private GetBolgListModel mGetBolgListModel;
    private int mPage = 1;
    private long id = -1;
    private int blogId = -1;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisdynamics);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long id) {
        Intent intent = new Intent(context, HisDynamicsActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("TA的动态");

        id = getIntent().getLongExtra("id", -1);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无动态");

        mAdapter = new PostingListAdapter(mContext, getmWidth(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
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
                    hisdynamicsTotopIv.setVisibility(View.VISIBLE);
                } else {
                    hisdynamicsTotopIv.setVisibility(View.GONE);
                }
            }
        });
        hisdynamicsTotopIv.setVisibility(View.GONE);
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
        OkHttpUtils.get().url(Common.Url_Get_My_Sceret + id + "/" + mPage).id(Common.NET_GET_MYSECRET)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_MYSECRET).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_MYSECRET) {
            mGetBolgListModel = mGson.fromJson(data, GetBolgListModel.class);
            if (mGetBolgListModel.getBody() == null || mGetBolgListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                LogUtils.e("mGetBolgListModel.getBody():" + mGetBolgListModel.getBody());
                mAdapter.addBody(mGetBolgListModel.getBody());
            }
        } else if (id == Common.NET_GET_BLOGCOLLECTION) {
            showToast("收藏成功");
            mAdapter.setFavoriteById(1, position);
        } else if (id == Common.NET_GET_BLOGCOLLECTION_NOT) {
            showToast("已取消收藏");
            mAdapter.setFavoriteById(0, position);
        } else if (id == Common.NET_ADD_BROWSE) {
            mAdapter.addBrowse(blogId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_MYSECRET);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_bloglist_browse_ll:
            case R.id.item_bloglist_root_ll:
            case R.id.item_bloglist_comment_ll:
                this.position = position;
                startActivityForResult(PostingsDetailActivity.buildIntent(mContext, mAdapter.getItem(position).getTitle()
                        , mAdapter.getItem(position).getBlog_id(), true), Common.toPostDetail);
                blogId = mAdapter.getItem(position).getBlog_id();
                OkHttpUtils.postString().url(Common.Url_Add_Browse + blogId).mediaType(Common.JSON)
                        .content("{}")
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE)
                        .build().execute(new MyStringCallback(mContext, HisDynamicsActivity.this, false));
                break;
            case R.id.item_bloglist_collection_ll:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                this.position = position;
                if (mAdapter.getItem(position).getFavorite_id() > 0) {//已收藏，点击取消收藏
                    OkHttpUtils.delete().url(Common.Url_Get_BlogCollection_Not + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION_NOT)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION_NOT).build()
                            .execute(new MyStringCallback(mContext, HisDynamicsActivity.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(mContext, HisDynamicsActivity.this, true));
                }
                break;
        }
    }

    @OnClick({R.id.hisdynamics_totop_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hisdynamics_totop_iv:
                pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
                break;
        }
    }
}
