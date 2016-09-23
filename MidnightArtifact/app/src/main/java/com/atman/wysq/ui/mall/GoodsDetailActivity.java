package com.atman.wysq.ui.mall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.GoodsDetailOneAdapter;
import com.atman.wysq.adapter.TabAdapter;
import com.atman.wysq.model.response.GetGoodsCommentResponseModel;
import com.atman.wysq.model.response.GoodsDetailsResponseModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.order.ConfirmationOrderActivity;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyListView;
import com.base.baselibs.widget.MyViewPager;
import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.ScrollViewListener;
import com.nostra13.universalimageloader.core.ImageLoader;
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
 * 时间 16/7/19 13:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class GoodsDetailActivity extends MyBaseActivity implements ScrollViewListener
        , AdapterInterface {

    @Bind(R.id.top_tab_layout)
    TabLayout topTabLayout;
    @Bind(R.id.part_goodsdetail_top_bg_iv)
    ImageView partGoodsdetailTopBgIv;
    @Bind(R.id.part_goodsdetail_top_bf_experience_tx)
    TextView partGoodsdetailTopBfExperienceTx;
    @Bind(R.id.part_goodsdetail_top_bf_coin_tx)
    TextView partGoodsdetailTopBfCoinTx;
    @Bind(R.id.part_goodsdetail_top_bf_tool_iv)
    ImageView partGoodsdetailTopBfToolIv;
    @Bind(R.id.part_goodsdetail_top_bf_tool_empty_tx)
    TextView partGoodsdetailTopBfToolEmptyTx;
    @Bind(R.id.part_goodsdetail_top_bf_name_tx)
    TextView partGoodsdetailTopBfNameTx;
    @Bind(R.id.part_goodsdetail_top_bf_price_tx)
    TextView partGoodsdetailTopBfPriceTx;
    @Bind(R.id.part_goodsdetail_top_bf_originalprice_tx)
    TextView partGoodsdetailTopBfOriginalpriceTx;
    @Bind(R.id.part_goodsdetail_top_bf_free_tx)
    TextView partGoodsdetailTopBfFreeTx;
    @Bind(R.id.part_goodsdetail_top_bf_salesvolume_tx)
    TextView partGoodsdetailTopBfSalesvolumeTx;
    @Bind(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;
    @Bind(R.id.goodsdetail_head_root_ll)
    LinearLayout goodsdetailHeadRootLl;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.goodsdetail_empty_tx)
    TextView goodsdetailEmptyTx;
    @Bind(R.id.goodsdetail_onefragment_lv)
    MyListView goodsdetailOnefragmentLv;
    @Bind(R.id.info_viewpager)
    MyViewPager infoViewpager;
    @Bind(R.id.goodsdetail_one_webview)
    WebView goodsdetailOneWebview;
    @Bind(R.id.item_goodsdetail_play_bt)
    Button itemGoodsdetailPlayBt;

    private Context mContext = GoodsDetailActivity.this;
    private int goodsId;
    private GoodsDetailsResponseModel mGoodsDetailsResponseModel;
    private List<Fragment> fragments;
    private TabAdapter fragPagerAdapter;
    private int headHight;
    private ObservableScrollView mScrollView;
    private GetGoodsCommentResponseModel mGetGoodsCommentResponseModel;

    private int page = 1;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initRefreshView(PullToRefreshBase.Mode.DISABLED, pullToRefreshScrollView);
        setBarTitleTx("商品详情");
        goodsId = getIntent().getIntExtra("goodsId", -1);
        LogUtils.e("goodsId:" + goodsId);

        headHight = getmHight() - DensityUtil.dp2px(mContext, 120);
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(getmWidth(), headHight);
        goodsdetailHeadRootLl.setLayoutParams(localObject);

        mScrollView = pullToRefreshScrollView.getRefreshableView();
        mScrollView.setScrollViewListener(this);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Category_Detail + goodsId)
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_CATEGORY_DETAIL).id(Common.NET_GET_CATEGORY_DETAIL).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    private void dohttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_Goods_comment + goodsId + "/" + page)
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GOODS_COMMENT).id(Common.NET_GET_GOODS_COMMENT).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_GET_CATEGORY_DETAIL) {
            mGoodsDetailsResponseModel = mGson.fromJson(data, GoodsDetailsResponseModel.class);
            updateUI();
            dohttp(false);
        } else if (id == Common.NET_GET_GOODS_COMMENT) {
            super.onStringResponse(data, response, id);
            mGetGoodsCommentResponseModel =
                    mGson.fromJson(data, GetGoodsCommentResponseModel.class);
            if (mGetGoodsCommentResponseModel.getBody() == null
                    || mGetGoodsCommentResponseModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                    goodsdetailEmptyTx.setVisibility(View.GONE);
                    goodsdetailOnefragmentLv.setVisibility(View.VISIBLE);
                } else {
                    if (topTabLayout.getSelectedTabPosition()==0) {
                        goodsdetailEmptyTx.setVisibility(View.GONE);
                    } else {
                        goodsdetailEmptyTx.setVisibility(View.VISIBLE);
                    }
                    goodsdetailOnefragmentLv.setVisibility(View.GONE);
                }

                if (topTabLayout.getSelectedTabPosition()==0) {
                    onLoad(PullToRefreshBase.Mode.DISABLED, pullToRefreshScrollView);
                } else {
                    onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshScrollView);
                }
            } else {
                if (topTabLayout.getSelectedTabPosition()==0) {
                    onLoad(PullToRefreshBase.Mode.DISABLED, pullToRefreshScrollView);
                } else {
                    onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshScrollView);
                }
                mAdapter.addBody(mGetGoodsCommentResponseModel.getBody());
            }
        } else if (id == Common.NET_ADD_LIKE) {
            mAdapter.setLikeById(mPosition);
        }
    }

    private void updateUI() {
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mGoodsDetailsResponseModel.getBody().getPic_img()
                , partGoodsdetailTopBgIv, MyBaseApplication.getApplication().getOptionsNot());
        partGoodsdetailTopBfToolEmptyTx.setVisibility(View.GONE);
        partGoodsdetailTopBfToolIv.setVisibility(View.GONE);
//        if (mGoodsDetailsResponseModel.getBody().getIcon() == null) {
//            partGoodsdetailTopBfToolEmptyTx.setVisibility(View.VISIBLE);
//            partGoodsdetailTopBfToolIv.setVisibility(View.GONE);
//        } else {
//            partGoodsdetailTopBfToolEmptyTx.setVisibility(View.GONE);
//            partGoodsdetailTopBfToolIv.setVisibility(View.VISIBLE);
//            ImageLoader.getInstance().displayImage(Common.ImageUrl + mGoodsDetailsResponseModel.getBody().getIcon()
//                    , partGoodsdetailTopBfToolIv, MyBaseApplication.getApplication().getOptionsNot());
//        }
//        if (mGoodsDetailsResponseModel.getBody().getPostage() > 0) {
            partGoodsdetailTopBfFreeTx.setVisibility(View.VISIBLE);
//        } else {
//            partGoodsdetailTopBfFreeTx.setVisibility(View.GONE);
//        }
        partGoodsdetailTopBfExperienceTx.setText("赠送经验：" + mGoodsDetailsResponseModel.getBody().getIntegral());
        partGoodsdetailTopBfCoinTx.setText("赠送金币：" + mGoodsDetailsResponseModel.getBody().getGold_coin());
        partGoodsdetailTopBfNameTx.setText(mGoodsDetailsResponseModel.getBody().getTitle());
        partGoodsdetailTopBfPriceTx.setText("¥" + mGoodsDetailsResponseModel.getBody().getDiscount_price());
        partGoodsdetailTopBfOriginalpriceTx.setText("¥" + mGoodsDetailsResponseModel.getBody().getPrice());
        partGoodsdetailTopBfOriginalpriceTx.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        partGoodsdetailTopBfSalesvolumeTx.setText("月销量：" + mGoodsDetailsResponseModel.getBody().getSales());

        initCategoryView();
    }

    private void initCategoryView() {
        fragments = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("图文详情");
        list.add("商品评论");
        for (int i = 0; i < list.size(); i++) {
            GoodsDetailOneFragment oneFragment = new GoodsDetailOneFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("images", mGoodsDetailsResponseModel.getBody());
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }

        fragPagerAdapter = new TabAdapter(getSupportFragmentManager());
        //设置显示的标题
        fragPagerAdapter.setList(list);
        //设置需要进行滑动的页面Fragment
        fragPagerAdapter.setFragments(fragments);

        infoViewpager.setAdapter(fragPagerAdapter);
        tabLayout.setupWithViewPager(infoViewpager);
        topTabLayout.setupWithViewPager(infoViewpager);
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        topTabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                infoViewpager.setCurrentItem(tabLayout.getSelectedTabPosition());
                selectItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        topTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                infoViewpager.setCurrentItem(topTabLayout.getSelectedTabPosition());
                selectItem(topTabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        initListView();
    }

    private void selectItem(int i) {
        if (i == 0) {
            goodsdetailEmptyTx.setVisibility(View.GONE);
            goodsdetailOneWebview.setVisibility(View.VISIBLE);
            goodsdetailOnefragmentLv.setVisibility(View.GONE);
            initRefreshView(PullToRefreshBase.Mode.DISABLED, pullToRefreshScrollView);
        } else {
            goodsdetailEmptyTx.setVisibility(View.VISIBLE);
            goodsdetailOneWebview.setVisibility(View.GONE);
            if (mAdapter != null && mAdapter.getCount() > 0) {
                goodsdetailEmptyTx.setVisibility(View.GONE);
                goodsdetailOnefragmentLv.setVisibility(View.VISIBLE);
            } else {
                goodsdetailEmptyTx.setVisibility(View.VISIBLE);
                goodsdetailOnefragmentLv.setVisibility(View.GONE);
            }
            initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshScrollView);
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        page = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshScrollView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        page += 1;
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    private GoodsDetailOneAdapter mAdapter;

    private void initListView() {
        mAdapter = new GoodsDetailOneAdapter(mContext, this);
        goodsdetailOnefragmentLv.setAdapter(mAdapter);

        String str = "";
        for (int i = 0; i < mGoodsDetailsResponseModel.getBody().getGoodsDetailImgs().size(); i++) {
            String url = "";
            if (mGoodsDetailsResponseModel.getBody().getGoodsDetailImgs().get(i).getImg().startsWith("http")) {
                url = mGoodsDetailsResponseModel.getBody().getGoodsDetailImgs().get(i).getImg();
            } else {
                url = Common.ImageUrl + mGoodsDetailsResponseModel.getBody().getGoodsDetailImgs().get(i).getImg();
            }
            str += "<img src=\"" + url + "\"/>";
        }

        String customHtml = getHtmlData(str);
        goodsdetailOneWebview.getSettings().setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            goodsdetailOneWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            goodsdetailOneWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        goodsdetailOneWebview.loadData(customHtml, "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CATEGORY_DETAIL);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOODS_COMMENT);
    }

    @OnClick(R.id.item_goodsdetail_play_bt)
    public void onClick() {
        if (!isLogin()) {
            showLogin();
        } else {
            startActivity(ConfirmationOrderActivity.buildIntent(mContext
                    , mGoodsDetailsResponseModel.getBody().getTitle()
                    ,mGoodsDetailsResponseModel.getBody().getPic_img()
                    ,mGoodsDetailsResponseModel.getBody().getDiscount_price()
                    ,mGoodsDetailsResponseModel.getBody().getGoods_id()));
        }
    }

    private void toLogin() {
        this.startActivity(new Intent(mContext, LoginActivity.class));
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{margin:0;padding:0;}img{max-width: 100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y >= headHight) {
            topTabLayout.setVisibility(View.VISIBLE);
        } else if (y <= headHight + DensityUtil.dp2px(mContext, 40)) {
            topTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_goodsdetail_head_rl:
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel != null && mAdapter.getItem(position).getUser_id() ==
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
                break;
            case R.id.item_goodsdetail_like_tv:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                OkHttpUtils.postString().url(Common.Url_Add_Like+"2/"+mAdapter.getItem(position).getGoods_comment_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .content("{}").mediaType(Common.JSON).id(Common.NET_ADD_LIKE).tag(Common.NET_ADD_LIKE)
                        .build().execute(new MyStringCallback(mContext, GoodsDetailActivity.this,true));
                break;
        }
        mAdapter.getItem(position);
    }
}
