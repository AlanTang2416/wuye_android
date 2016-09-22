package com.atman.wysq.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.PostingListAdapter;
import com.atman.wysq.model.response.GetBolgListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
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
 * 时间 16/8/24 16:49
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostingsByClassificationFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;

    private int mPage = 1;
    private int id;
    private int typeId;
    private int blogId;
    private int position = -1;

    private PostingListAdapter mAdapter;
    private GetBolgListModel mGetBolgListModel;
    private List<GetBolgListModel.BodyEntity> topList = new ArrayList<>();

    private View mTopRootView;
    private LinearLayout postingsTopLl;
    private View mEmpty;
    private TextView mEmptyTX;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postingsbyclassification, null);
        Bundle b = getArguments();
        id = b.getInt("id");
        typeId = b.getInt("typeId");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mEmpty = LayoutInflater.from(getActivity()).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无帖子");

        mTopRootView = LayoutInflater.from(getActivity()).inflate(R.layout.part_posting_top_root_view, null);
        postingsTopLl = (LinearLayout) mTopRootView.findViewById(R.id.postings_top_ll);

        mAdapter = new PostingListAdapter(getActivity(), getmWidth(), this);
        mAdapter.setTypeId(typeId);
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
        mPage = 1;
        dohttp(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
                final List<GetBolgListModel.BodyEntity> bottomList = mGetBolgListModel.getBody();
                if (mPage == 1) {
                    postingsTopLl.removeAllViews();
                    for (int i = 0; i < bottomList.size(); i++) {
                        if (bottomList.get(i).getStick() == 1) {
                            topList.add(bottomList.get(i));
                            View mTopChildrenView = LayoutInflater.from(getActivity()).inflate(R.layout.part_posting_top_children_view, null);
                            TextView mTopChildrenTx = (TextView) mTopChildrenView.findViewById(R.id.part_posting_top_children_title_tx);
                            mTopChildrenTx.setText(bottomList.get(i).getTitle());
                            mTopChildrenTx.setTag(bottomList.get(i).getBlog_id());
                            final int finalI = i;
                            mTopChildrenTx.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toPostingsDetail((Integer) v.getTag(), "", bottomList.get(finalI).getVip_level());
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
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLOGLIST).build().execute(new MyStringCallback(getActivity(), this, b));
    }

    private void toPostingsDetail(int Id, String title, int Vip_level) {
        blogId = Id;
        startActivity(PostingsDetailActivity.buildIntent(getActivity(), title, blogId, false, Vip_level));
        OkHttpUtils.postString().url(Common.Url_Add_Browse+blogId).mediaType(Common.JSON)
                .content("{}")
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE)
                .build().execute(new MyStringCallback(getActivity(), this, false));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_bloglist_head_rl:
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel==null) {
                    showLogin();
                    return;
                }
                if (mAdapter.getItem(position).getUser_id()==
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivity(OtherPersonalActivity.buildIntent(getActivity(), mAdapter.getItem(position).getUser_id()));
                break;
            case R.id.item_bloglist_browse_ll:
            case R.id.item_bloglist_root_ll:
            case R.id.item_bloglist_comment_ll:
                toPostingsDetail(mAdapter.getItem(position).getBlog_id(), mAdapter.getItem(position).getTitle(), mAdapter.getItem(position).getVip_level());
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
                            .execute(new MyStringCallback(getActivity(), PostingsByClassificationFragment.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(getActivity(), PostingsByClassificationFragment.this, true));
                }
                break;
        }
    }

    @OnClick(R.id.postings_totop_iv)
    public void onClick() {
        pullToRefreshListView.getRefreshableView().smoothScrollToPosition(0);
    }
}
