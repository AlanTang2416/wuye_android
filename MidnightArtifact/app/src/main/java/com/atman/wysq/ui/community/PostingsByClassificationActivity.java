package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.PostingListAdapter;
import com.atman.wysq.model.response.GetBolgListModel;
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
 * 时间 16/7/27 15:04
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostingsByClassificationActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.postings_tab_new_rt)
    RadioButton postingsTabNewRt;
    @Bind(R.id.postings_tab_hot_rt)
    RadioButton postingsTabHotRt;
    @Bind(R.id.postings_tab_boutique_rt)
    RadioButton postingsTabBoutiqueRt;
    @Bind(R.id.postings_top_tab_rg)
    RadioGroup postingsTopTabRg;
    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;

    private Context mContext = PostingsByClassificationActivity.this;

    private View mTopRootView;
    private LinearLayout postingsTopLl;
    private View mEmpty;
    private TextView mEmptyTX;
    private RelativeLayout rightRl;

    private PostingListAdapter mAdapter;
    private GetBolgListModel mGetBolgListModel;
    private List<GetBolgListModel.BodyEntity> topList = new ArrayList<>();

    private String title;
    private String canPost;
    private int id;
    private int position = -1;
    private int typeId = 0; //0：热门 2:精品 3:最新
    private int mPage = 1;
    private int blogId;

    public PostingsByClassificationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_postingsbyclassification);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String title, int id, String can_post) {
        Intent intent = new Intent(context, PostingsByClassificationActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        intent.putExtra("can_post", can_post);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        title = getIntent().getStringExtra("title");
        canPost = getIntent().getStringExtra("can_post");
        id = getIntent().getIntExtra("id", -1);

        setBarTitleTx(title);
        setBarRightIv(R.mipmap.square_icon_edit);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    canToPost();
                }
            }
        });

        initBottomBar();
        initListView();
    }

    private void canToPost() {
        boolean isCan = false;
        if (canPost.equals("111")) {//所有用户都可以
            isCan = true;
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("100")) {//男性用户才可以
            if (MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("只有男性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("010")) {//女性用户才可以
            if (MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")) {
                isCan = true;
            } else {
                showToast("只有女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("001")) {//认证女性用户才可以
            if (MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getVerify_status()==1) {
                isCan = true;
            } else {
                showToast("已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("101")) {//男用户和认证女性用户才可以
            if ((MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getVerify_status()==1)
                    || MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("男性用户和已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("110")) {//男性用户和女性用户才可以发帖
            if (MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")
                    || MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("男性用户和女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("011")) {//女性用户和女性用户才可以发帖
            if ((MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getVerify_status()==1)
                    || MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")) {
                isCan = true;
            } else {
                showToast("女性用户和已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetUserInfoModel != null && canPost.equals("000")) {//管理员才可以发帖
            if (MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getType()==2) {
                isCan = true;
            } else {
                showToast("只有管理员用户才可以发帖");
            }
        }
        if (isCan) {
            startActivity(PostActivity.buildIntent(mContext, id));
        }
    }

    private void initBottomBar() {
        postingsTopTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mAdapter.clearData();
                switch (checkedId) {
                    case R.id.postings_tab_new_rt:
                        postingsTabNewRt.performClick();
                        typeId = 3;
                        mPage = 1;
                        mAdapter.clearData();
                        mAdapter.setTypeId(typeId);
                        dohttp(true);
                        break;
                    case R.id.postings_tab_hot_rt:
                        postingsTabHotRt.performClick();
                        typeId = 0;
                        mPage = 1;
                        mAdapter.clearData();
                        mAdapter.setTypeId(typeId);
                        dohttp(true);
                        break;
                    case R.id.postings_tab_boutique_rt:
                        postingsTabBoutiqueRt.performClick();
                        typeId = 2;
                        mPage = 1;
                        mAdapter.clearData();
                        mAdapter.setTypeId(typeId);
                        dohttp(true);
                        break;
                }
            }
        });
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无帖子");

        mTopRootView = LayoutInflater.from(mContext).inflate(R.layout.part_posting_top_root_view, null);
        postingsTopLl = (LinearLayout) mTopRootView.findViewById(R.id.postings_top_ll);

        mAdapter = new PostingListAdapter(mContext, getmWidth(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.getRefreshableView().addHeaderView(mTopRootView);
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
                    postingsTotopIv.setVisibility(View.VISIBLE);
                } else {
                    postingsTotopIv.setVisibility(View.GONE);
                }
            }
        });
        postingsTotopIv.setVisibility(View.GONE);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_BLOGLIST) {
            mGetBolgListModel = mGson.fromJson(data, GetBolgListModel.class);
            if (mGetBolgListModel.getBody() == null
                    || mGetBolgListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                List<GetBolgListModel.BodyEntity> bottomList = mGetBolgListModel.getBody();
                if (mPage == 1) {
                    postingsTopLl.removeAllViews();
                    for (int i = 0; i < bottomList.size(); i++) {
                        if (bottomList.get(i).getStick() == 1) {
                            topList.add(bottomList.get(i));
                            View mTopChildrenView = LayoutInflater.from(mContext).inflate(R.layout.part_posting_top_children_view, null);
                            TextView mTopChildrenTx = (TextView) mTopChildrenView.findViewById(R.id.part_posting_top_children_title_tx);
                            mTopChildrenTx.setText(bottomList.get(i).getTitle());
                            mTopChildrenTx.setTag(bottomList.get(i).getBlog_id());
                            mTopChildrenTx.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toPostingsDetail((Integer) v.getTag(), "");

                                }
                            });
                            postingsTopLl.addView(mTopChildrenView);
                            bottomList.remove(i);
                            i--;
                        }
                    }
                }
                if (topList.size() == 0) {
                    postingsTopLl.setVisibility(View.GONE);
                } else {
                    postingsTopLl.setVisibility(View.VISIBLE);
                }
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addBody(bottomList);
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
        OkHttpUtils.get().url(Common.Url_Get_BlogList + id + "/" + typeId + "/" + mPage).id(Common.NET_GET_BLOGLIST)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_BLOGLIST).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_bloglist_browse_ll:
            case R.id.item_bloglist_root_ll:
            case R.id.item_bloglist_comment_ll:
                toPostingsDetail(mAdapter.getItem(position).getBlog_id(), mAdapter.getItem(position).getTitle());
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
                            .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION_NOT).build()
                            .execute(new MyStringCallback(mContext, PostingsByClassificationActivity.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(mContext, PostingsByClassificationActivity.this, true));
                }
                break;
        }
    }

    private void toPostingsDetail(int Id, String title) {
        blogId = Id;
        startActivity(PostingsDetailActivity.buildIntent(mContext, title, blogId, false));
        OkHttpUtils.postString().url(Common.Url_Add_Browse+blogId).mediaType(Common.JSON)
                .content("{}")
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE)
                .build().execute(new MyStringCallback(mContext, PostingsByClassificationActivity.this, false));

    }

    @OnClick(R.id.postings_totop_iv)
    public void onClick() {
        pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
    }
}
