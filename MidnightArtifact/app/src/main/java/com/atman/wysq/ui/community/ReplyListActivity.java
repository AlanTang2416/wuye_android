package com.atman.wysq.ui.community;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MySecretListAdapter;
import com.atman.wysq.model.response.GetMyCollectionModel;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/8 14:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ReplyListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.reply_bar_title_one_tx)
    TextView replyBarTitleOneTx;
    @Bind(R.id.reply_bar_title_one_iv)
    ImageView replyBarTitleOneIv;
    @Bind(R.id.reply_bar_title_one_ll)
    LinearLayout replyBarTitleOneLl;
    @Bind(R.id.reply_bar_title_two_tx)
    TextView replyBarTitleTwoTx;
    @Bind(R.id.reply_bar_title_two_iv)
    ImageView replyBarTitleTwoIv;
    @Bind(R.id.reply_bar_title_two_ll)
    LinearLayout replyBarTitleTwoLl;
    @Bind(R.id.reply_bar_title_back_iv)
    ImageView replyBarTitleBackIv;
    @Bind(R.id.reply_bar_title_back_ll)
    LinearLayout replyBarTitleBackLl;

    private Context mContext = ReplyListActivity.this;

    private int mPage = 1;
    private int mStateId = 0;//0:我评论的 1:评论我的
    private int position;
    private int blogId;

    private MySecretListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replylist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        View mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        TextView mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无回复");

        mAdapter = new MySecretListAdapter(mContext, getmWidth(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mAdapter);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_UserComment + mStateId + "/" + mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERCOMMENT).id(Common.NET_GET_USERCOMMENT).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        doHttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        doHttp(false);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERCOMMENT) {
            GetMyCollectionModel mGetMyCollectionModel = mGson.fromJson(data, GetMyCollectionModel.class);
            if (mGetMyCollectionModel.getBody() == null
                    || mGetMyCollectionModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addBody(mGetMyCollectionModel.getBody());
            }
        } else if (id==Common.NET_GET_BLOGCOLLECTION) {
            showToast("收藏成功");
            mAdapter.setFavoriteById(1, position);
        } else if (id==Common.NET_GET_BLOGCOLLECTION_NOT) {
            showToast("已取消收藏");
            mAdapter.setFavoriteById(0, position);
        } else if (id == Common.NET_ADD_BROWSE) {
            mAdapter.addBrowse(blogId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERCOMMENT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
    }

    @OnClick({R.id.reply_bar_title_one_ll, R.id.reply_bar_title_two_ll, R.id.reply_bar_title_back_iv
            , R.id.reply_bar_title_back_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reply_bar_title_one_ll:
                mStateId = 0;
                mPage = 1;
                mAdapter.setmStateId(0);
                mAdapter.clearData();
                replyBarTitleOneTx.setTextColor(getResources().getColor(R.color.reply_top_selected_tx));
                replyBarTitleOneIv.setVisibility(View.VISIBLE);
                replyBarTitleTwoTx.setTextColor(getResources().getColor(R.color.reply_top_unselect_tx));
                replyBarTitleTwoIv.setVisibility(View.INVISIBLE);
                doHttp(true);
                break;
            case R.id.reply_bar_title_two_ll:
                mStateId = 1;
                mPage = 1;
                mAdapter.setmStateId(1);
                mAdapter.clearData();
                replyBarTitleOneTx.setTextColor(getResources().getColor(R.color.reply_top_unselect_tx));
                replyBarTitleOneIv.setVisibility(View.INVISIBLE);
                replyBarTitleTwoTx.setTextColor(getResources().getColor(R.color.reply_top_selected_tx));
                replyBarTitleTwoIv.setVisibility(View.VISIBLE);
                doHttp(true);
                break;
            case R.id.reply_bar_title_back_iv:
            case R.id.reply_bar_title_back_ll:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_bloglist_head_rl:
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel==null) {
                    showLogin();
                    return;
                }
                if (mAdapter.getItem(position).getUser_id() ==
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
                break;
            case R.id.item_bloglist_browse_ll:
            case R.id.item_bloglist_root_ll:
            case R.id.item_bloglist_comment_ll:
                blogId = mAdapter.getItem(position).getBlog_id();
                startActivity(PostingsDetailActivity.buildIntent(mContext, mAdapter.getItem(position).getTitle()
                        , blogId, false, mAdapter.getItem(position).getVip_level() ));
                OkHttpUtils.postString().url(Common.Url_Add_Browse+blogId).mediaType(Common.JSON)
                        .content("{}")
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE)
                        .build().execute(new MyStringCallback(mContext, ReplyListActivity.this, false));
                break;
            case R.id.item_bloglist_collection_ll:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                this.position = position;
                if (mAdapter.getItem(position).getFavorite_id()>0) {//已收藏，点击取消收藏
                    OkHttpUtils.delete().url(Common.Url_Get_BlogCollection_Not + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION_NOT)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION_NOT).build()
                            .execute(new MyStringCallback(mContext, ReplyListActivity.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(mContext, ReplyListActivity.this, true));
                }
                break;
        }
    }
}
