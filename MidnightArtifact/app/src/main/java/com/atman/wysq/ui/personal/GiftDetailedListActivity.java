package com.atman.wysq.ui.personal;

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
import com.atman.wysq.adapter.GiftDetailedListGridViewAdapter;
import com.atman.wysq.model.response.GiftDetailedListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by vavid on 2016/9/21.
 */
public class GiftDetailedListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.giftdetailedlist_totop_iv)
    ImageView giftdetailedlistTotopIv;

    private Context mContext = GiftDetailedListActivity.this;

    private GiftDetailedListGridViewAdapter mAdapter;
    private int giftId;
    private int mPage = 1;
    private GiftDetailedListModel mGiftDetailedListModel;

    private View mEmpty;
    private TextView mEmptyTX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftdetailedlist);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int giftId) {
        Intent intent = new Intent(context, GiftDetailedListActivity.class);
        intent.putExtra("giftId", giftId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        giftId = getIntent().getIntExtra("giftId", 0);

        setBarTitleTx("礼物清单");
        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("还没有人送你礼物哦！");

        mAdapter = new GiftDetailedListGridViewAdapter(mContext, this);
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
                if (firstVisibleItem >= 5 && totalItemCount!=0) {
                    giftdetailedlistTotopIv.setVisibility(View.VISIBLE);
                } else {
                    giftdetailedlistTotopIv.setVisibility(View.GONE);
                }
            }
        });
        giftdetailedlistTotopIv.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        OkHttpUtils.get().url(Common.Url_Get_GiftDetailedList + giftId + "/" + mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GIFTDETAILEDLIST).id(Common.NET_GET_GIFTDETAILEDLIST).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GIFTDETAILEDLIST) {
            mGiftDetailedListModel = mGson.fromJson(data, GiftDetailedListModel.class);
            if (mGiftDetailedListModel.getBody() == null || mGiftDetailedListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addShop(mGiftDetailedListModel.getBody());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GIFTDETAILEDLIST);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
    }
}
