package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/8 11:38
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MySecretListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    private Context mContext = MySecretListActivity.this;

    private TextView barRightTx;

    private int mPage = 1;
    private int mStateId = 2;//1:未审核 2:已通过 3:未通过 4:已删除
    private int position;
    private int blogId;

    private MySecretListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysecretlist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("我的秘密");

        barRightTx = setBarRightTx("已通过");
        Drawable drawable = getResources().getDrawable(R.mipmap.post_main_img_filter);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        barRightTx.setCompoundDrawables(drawable, null, null, null);
        barRightTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWin(v);
            }
        });
        initListView();
    }

    private void showPopWin(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.top_secret_popwin_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT
                ,RelativeLayout.LayoutParams.MATCH_PARENT);

        view.findViewById(R.id.top_win_secret_one_tx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                toChangeStatu(2);
                barRightTx = setBarRightTx("已通过");
            }
        });
        view.findViewById(R.id.top_win_secret_two_tx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                toChangeStatu(1);
                barRightTx = setBarRightTx("审核中");
            }
        });
        view.findViewById(R.id.top_win_secret_three_tx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                toChangeStatu(3);
                barRightTx = setBarRightTx("未通过");
            }
        });
        view.findViewById(R.id.top_win_secret_four_tx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                toChangeStatu(4);
                barRightTx = setBarRightTx("已删除");
            }
        });
        view.findViewById(R.id.top_win_secret_root_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
    }

    private void toChangeStatu(int num) {
        mStateId = num;
        mPage = 1;
        mAdapter.clearData();
        doHttp(true);
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

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        View mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        TextView mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无秘密");

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
        if (MyBaseApplication.mGetMyUserIndexModel!=null) {
            OkHttpUtils.get().url(Common.Url_Get_My_Sceret + MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId() + "/"  + mStateId + "/" + mPage)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_MYSECRET).id(Common.NET_GET_MYSECRET).build()
                    .execute(new MyStringCallback(mContext, this, b));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_MYSECRET) {
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
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_MYSECRET);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_bloglist_head_rl:
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
                this.position = position;
                startActivityForResult(PostingsDetailActivity.buildIntent(mContext, mAdapter.getItem(position).getTitle()
                        , mAdapter.getItem(position).getBlog_id(), true, mAdapter.getItem(position).getVip_level()), Common.toPostDetail);
                blogId = mAdapter.getItem(position).getBlog_id();
                OkHttpUtils.postString().url(Common.Url_Add_Browse+blogId).mediaType(Common.JSON)
                        .content("{}")
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE)
                        .build().execute(new MyStringCallback(mContext, MySecretListActivity.this, false));
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
                            .execute(new MyStringCallback(mContext, MySecretListActivity.this, true));
                } else {//未收藏，点击收藏
                    OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + mAdapter.getItem(position).getBlog_id())
                            .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_GET_BLOGCOLLECTION).build()
                            .execute(new MyStringCallback(mContext, MySecretListActivity.this, true));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toPostDetail) {
            int id = data.getIntExtra("id", -1);
            if (id!=-1) {
                mAdapter.deleteById(position);
            }
        }
    }
}
