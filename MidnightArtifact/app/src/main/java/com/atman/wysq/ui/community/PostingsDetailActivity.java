package com.atman.wysq.ui.community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atman.wysq.R;
import com.atman.wysq.adapter.PostingsDetailsCommentAdapter;
import com.atman.wysq.adapter.RewardGridViewAdapter;
import com.atman.wysq.model.request.AddCommentModel;
import com.atman.wysq.model.response.AddCommentResultModel;
import com.atman.wysq.model.response.AddRewardModel;
import com.atman.wysq.model.response.GetBlogDetailModel;
import com.atman.wysq.model.response.GetPostingsDetailsCommentListModel;
import com.atman.wysq.model.response.GetRewardListModel;
import com.atman.wysq.model.response.GoodsDetailsResponseModel;
import com.atman.wysq.ui.PictureBrowsingActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.mall.GoodsDetailActivity;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.utils.ShareHelper;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.widget.ShareDialog;
import com.atman.wysq.widget.face.SmileUtils;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/28 17:32
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostingsDetailActivity extends MyBaseActivity implements AdapterInterface
        , UMShareListener, View.OnTouchListener {

    @Bind(R.id.blogdetail_comment_lv)
    PullToRefreshListView blogdetailCommentLv;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.blogdetail_send_bt)
    Button blogdetailSendBt;

    private Context mContext = PostingsDetailActivity.this;
    private ListView mListView;

    private String tilte;
    private int bolgId;
    private int page = 1;
    private boolean isLast = false;
    private int mPosition = -1;

    private GetBlogDetailModel mGetBlogDetailModel;
    private PostingsDetailsCommentAdapter mAdapter;
    private GetPostingsDetailsCommentListModel mGetPostingsDetailsCommentListModel;
    private GoodsDetailsResponseModel mGoodsDetailsResponseModel;

    private int favoriteId = 0;
    private int vipLevel = 0;
    private String favoriteStr = "收藏";
    private boolean isMy = false;
    private long blogUserId;
    private boolean isAnonymity = false;
    private int isReplay = 0;
    private String anonymityImg;
    private String imgStr = "";

    private View headView;
    private ImageView blogdetailHeadImg;
    private ImageView blogdetailSvipIv;
    private ImageView blogdetailVerifyImg;
    private ImageView blogdetailGenderImg;
    private ImageView blogdetailGoodsIv;
    private ImageView blogdetailFlowerIv;
    private TextView blogdetailVipTx;
    private TextView blogdetailNameTx;
    private TextView blogdetailLevelTx;
    private TextView bloglistTimeTx;
    private TextView blogdetailTitleTx;
    private TextView blogdetailGoodsNameIv;
    private TextView blogdetailGoodsPriceIv;
    private TextView blogdetailFlowerTv;
    private RelativeLayout blogdetailHeadRl;
    private RelativeLayout blogdetailGoodsRl;
    private RelativeLayout blogdetailTopRl;
    private LinearLayout blogdetailContentLl;
    private WebView blogdetailContentWb;
    private LinearLayout blogdetailFlowerLl;
    private GridView blogdetailFlowerGv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_postingsdetail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String tilte, int id, boolean isMy, int vipLevel) {
        Intent intent = new Intent(context, PostingsDetailActivity.class);
        intent.putExtra("tilte", tilte);
        intent.putExtra("id", id);
        intent.putExtra("isMy", isMy);
        intent.putExtra("vipLevel", vipLevel);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        tilte = getIntent().getStringExtra("tilte");
        bolgId = getIntent().getIntExtra("id", -1);
        vipLevel = getIntent().getIntExtra("vipLevel", 0);
        isMy = getIntent().getBooleanExtra("isMy", false);
        LogUtils.e("id:" + bolgId + ",isMy:" + isMy);

        setBarTitleTx(tilte);

        initRefreshView(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
        mListView = blogdetailCommentLv.getRefreshableView();
        headView = LayoutInflater.from(mContext).inflate(R.layout.part_postingsdetail_head_view, null);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    cancelIM(view);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = mListView.getChildAt(0);
                if (c == null) {
                    return;
                }
                int firstVisiblePosition = mListView.getFirstVisiblePosition();
                int top = c.getTop();
                if ((headView.getHeight() >= getmHight() && (-top >= getmHight() || firstVisiblePosition > 1))
                        || (headView.getHeight() < getmHight()) && (headView.getHeight() + firstVisiblePosition * c.getHeight()) >= getmHight()) {
                    postingsTotopIv.setVisibility(View.VISIBLE);
                } else {
                    postingsTotopIv.setVisibility(View.GONE);
                }
            }
        });

        setBarRightIv(R.mipmap.postingsdetails_right_ic);
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomImg(v);
            }
        });

        initListView();

        blogdetailCommentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter != null && position > 1) {
                    GetPostingsDetailsCommentListModel.BodyEntity mBodyEntity = mAdapter.getItem(position - 2);
                    startActivity(CommentChildrenListActivity.buildIntent(mContext, mBodyEntity.getBlog_id(), mBodyEntity.getBlog_comment_id()
                            , mBodyEntity.getIcon(), mBodyEntity.getVerify_status(), mBodyEntity.getUser_name()
                            , mBodyEntity.getSex(), mBodyEntity.getUserLevel(), mBodyEntity.getCreate_time()
                            , mBodyEntity.getUser_id(), mBodyEntity.getContent(), blogUserId, isAnonymity, anonymityImg, isReplay, mBodyEntity.getVip_level()));
                }
            }
        });
        blogdetailSendBt.setOnTouchListener(this);
    }

    private void sendMessage(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }

        if (llFacechoose.getVisibility() == View.VISIBLE) {
            llFacechoose.setVisibility(View.GONE);
        }
        String str = blogdetailAddcommentEt.getText().toString().trim();
        if (!str.isEmpty()) {
            AddCommentModel mAddCommentModel = new AddCommentModel(bolgId, str);
            OkHttpUtils.postString().url(Common.Url_Add_Comment).mediaType(Common.JSON)
                    .content(mGson.toJson(mAddCommentModel))
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .id(Common.NET_ADD_COMMENT).tag(Common.NET_ADD_COMMENT)
                    .build().execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_BlogDetail + bolgId).id(Common.NET_GET_BLOGDETAIL)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLOGDETAIL).build().execute(new MyStringCallback(mContext, this, true));
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_BlogDetail_CommentList + bolgId + "/" + page).id(Common.NET_GET_BLOGDETAIL_COMMENTLIST)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLOGDETAIL_COMMENTLIST).build().execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_BLOGDETAIL) {
            mGetBlogDetailModel = mGson.fromJson(data, GetBlogDetailModel.class);

            updateUI();

            if (mGetBlogDetailModel.getBody().size() > 0 &&
                    mGetBlogDetailModel.getBody().get(0).getGoods_id() > 0) {
                blogdetailGoodsRl.setVisibility(View.VISIBLE);
//                blogdetailTitleTx.setVisibility(View.GONE);
                blogdetailTopRl.setVisibility(View.GONE);
                blogdetailFlowerLl.setVisibility(View.GONE);
                OkHttpUtils.get().url(Common.Url_Get_Category_Detail + mGetBlogDetailModel.getBody().get(0).getGoods_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_CATEGORY_DETAIL).id(Common.NET_GET_CATEGORY_DETAIL).build()
                        .execute(new MyStringCallback(mContext, this, true));
            } else {
                blogdetailGoodsRl.setVisibility(View.GONE);
//                blogdetailTitleTx.setVisibility(View.VISIBLE);
                blogdetailTopRl.setVisibility(View.VISIBLE);
                blogdetailFlowerLl.setVisibility(View.VISIBLE);
                OkHttpUtils.get().url(Common.Url_Get_Award_List + mGetBlogDetailModel.getBody().get(0).getBlog_id()).id(Common.NET_GET_AWARDLIST)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_GET_AWARDLIST).build().execute(new MyStringCallback(mContext, this, true));
            }
        } else if (id == Common.NET_GET_BLOGDETAIL_COMMENTLIST) {
            mGetPostingsDetailsCommentListModel = mGson.fromJson(data, GetPostingsDetailsCommentListModel.class);
            if (mGetPostingsDetailsCommentListModel.getBody() == null
                    || mGetPostingsDetailsCommentListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0 && mGetPostingsDetailsCommentListModel.getBody().size() > 0) {
                    showToast("没有更多");
                }
                isLast = true;
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, blogdetailCommentLv);
            } else {
                if (mGetPostingsDetailsCommentListModel.getBody().size() < 20) {
                    isLast = true;
                } else {
                    isLast = false;
                }
                onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
                mAdapter.addBody(mGetPostingsDetailsCommentListModel.getBody());
            }
        } else if (id == Common.NET_GET_BLOGCOLLECTION) {
            favoriteId = 1;
            showToast("收藏成功");
        } else if (id == Common.NET_GET_BLOGCOLLECTION_NOT) {
            favoriteId = 0;
            showToast("已取消收藏");
        } else if (id == Common.NET_GET_CATEGORY_DETAIL) {
            mGoodsDetailsResponseModel = mGson.fromJson(data, GoodsDetailsResponseModel.class);
            blogdetailGoodsNameIv.setText(mGoodsDetailsResponseModel.getBody().getTitle());
            blogdetailGoodsPriceIv.setText("¥ " + mGoodsDetailsResponseModel.getBody().getDiscount_price());
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mGoodsDetailsResponseModel.getBody().getPic_img()
                    , blogdetailGoodsIv, MyBaseApplication.getApplication().getOptionsNot());
        } else if (id == Common.NET_ADD_COMMENT) {
            if (isLast) {
                AddCommentResultModel mAddCommentResultModel = mGson.fromJson(data, AddCommentResultModel.class);
                GetPostingsDetailsCommentListModel.BodyEntity body = new GetPostingsDetailsCommentListModel.BodyEntity();
                body.setContent(blogdetailAddcommentEt.getText().toString().trim());
                body.setCreate_time(System.currentTimeMillis());
                body.setIcon(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon());
                body.setSex(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex());
                body.setVerify_status(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status());
                body.setUserLevel(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUserLevel());
                body.setUser_name(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getNick_name());
                body.setUser_id(MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id());
                body.setBlog_comment_id(mAddCommentResultModel.getBody());
                body.setBlog_id(bolgId);
                mAdapter.addBody(body);
            }
            blogdetailAddcommentEt.setText("");
            showToast("评论成功");
            if (isReplay == 0) {
                Toast.makeText(mContext, "+1经验值", Toast.LENGTH_LONG).show();
                isReplay = 1;
            }
        } else if (id == Common.NET_ADD_LIKE) {
            mAdapter.setLikeById(mPosition);
        } else if (id == Common.NET_ADD_BLACKLIST) {
            showToast("已成功拉黑");
        } else if (id == Common.NET_ADD_AWARD) {
            AddRewardModel AddRewardModelm = mGson.fromJson(data, AddRewardModel.class);
            showToast("打赏成功");
            OkHttpUtils.get().url(Common.Url_Get_Award_List + mGetBlogDetailModel.getBody().get(0).getBlog_id()).id(Common.NET_GET_AWARDLIST)
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_AWARDLIST).build().execute(new MyStringCallback(mContext, this, true));
        } else if (id == Common.NET_GET_AWARDLIST) {
            GetRewardListModel mGetRewardListModel = mGson.fromJson(data, GetRewardListModel.class);
            int num = 0;
            for (int i = 0; i < mGetRewardListModel.getBody().size(); i++) {
                num += mGetRewardListModel.getBody().get(i).getUser_award_gold_num();
            }
            if (num == 0) {
                blogdetailFlowerTv.setText("暂时无人献花");
            } else {
                blogdetailFlowerTv.setText(num + "朵鲜花");
            }
            if (num > 0) {
                RewardGridViewAdapter mRewardListAdapter = new RewardGridViewAdapter(mContext, mGetRewardListModel.getBody());
                blogdetailFlowerGv.setAdapter(mRewardListAdapter);
                blogdetailFlowerGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(BlogRewardListActivty.buildIntent(mContext, mGetBlogDetailModel.getBody().get(0).getBlog_id()));
                    }
                });
            }
        } else if (id == Common.NET_DELETE_POST) {
            Intent mIntent = new Intent();
            mIntent.putExtra("id", id);
            setResult(RESULT_OK, mIntent);
            finish();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        page = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        page += 1;
        doHttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        page = 1;
        mAdapter.clearData();
        doHttp(false);
    }

    private void initListView() {
        blogdetailHeadImg = (ImageView) headView.findViewById(R.id.blogdetail_head_img);
        blogdetailVerifyImg = (ImageView) headView.findViewById(R.id.blogdetail_verify_img);
        blogdetailGenderImg = (ImageView) headView.findViewById(R.id.blogdetail_gender_img);
        blogdetailSvipIv = (ImageView) headView.findViewById(R.id.blogdetail_svip_iv);
        blogdetailGoodsIv = (ImageView) headView.findViewById(R.id.blogdetail_goods_iv);
        blogdetailNameTx = (TextView) headView.findViewById(R.id.blogdetail_name_tx);
        blogdetailVipTx = (TextView) headView.findViewById(R.id.blogdetail_vip_tx);
        blogdetailLevelTx = (TextView) headView.findViewById(R.id.blogdetail_level_tx);
        bloglistTimeTx = (TextView) headView.findViewById(R.id.bloglist_time_tx);
        blogdetailTitleTx = (TextView) headView.findViewById(R.id.blogdetail_title_tx);
        blogdetailGoodsNameIv = (TextView) headView.findViewById(R.id.blogdetail_goods_name_iv);
        blogdetailGoodsPriceIv = (TextView) headView.findViewById(R.id.blogdetail_goods_price_iv);
        blogdetailHeadRl = (RelativeLayout) headView.findViewById(R.id.blogdetail_head_rl);
        blogdetailHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGetBlogDetailModel!=null && mGetBlogDetailModel.getBody().get(0).getAnonymityUser() == null) {
                    startActivity(OtherPersonalActivity.buildIntent(mContext, mGetBlogDetailModel.getBody().get(0).getUser_id()));
                }
            }
        });
        blogdetailGoodsRl = (RelativeLayout) headView.findViewById(R.id.blogdetail_goods_rl);
        blogdetailGoodsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(GoodsDetailActivity.buildIntent(mContext,
                        mGoodsDetailsResponseModel.getBody().getGoods_id()));
            }
        });
        blogdetailTopRl = (RelativeLayout) headView.findViewById(R.id.blogdetail_top_rl);
        blogdetailContentLl = (LinearLayout) headView.findViewById(R.id.blogdetail_content_ll);
        blogdetailContentWb = (WebView) headView.findViewById(R.id.blogdetail_content_wb);
        blogdetailFlowerGv = (GridView) headView.findViewById(R.id.blogdetail_flower_gv);
        blogdetailFlowerTv = (TextView) headView.findViewById(R.id.blogdetail_flower_tv);
        blogdetailFlowerLl = (LinearLayout) headView.findViewById(R.id.blogdetail_flower_ll);
        blogdetailFlowerIv = (ImageView) headView.findViewById(R.id.blogdetail_flower_iv);
        blogdetailFlowerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (mGetBlogDetailModel.getBody().get(0).getUser_id()
                            == MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                        showToast("亲，这是你自己的帖子哦");
                    } else {
                        showWran();
                    }
                }
            }
        });

        mAdapter = new PostingsDetailsCommentAdapter(mContext, this);
        blogdetailCommentLv.getRefreshableView().addHeaderView(headView);
        blogdetailCommentLv.setAdapter(mAdapter);
    }

    public void showWran() {
        int CostGolden = MyBaseApplication.mGetGoldenRoleModel.getBody().get("5").getCost_golden();
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("献花一次将花费您" + CostGolden + "个金币哦！");
        builder.setPositiveButton("继续献花", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.postString().url(Common.Url_Add_Award)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .content("{\"oid\":" + bolgId + "}").mediaType(Common.JSON).id(Common.NET_ADD_AWARD).tag(Common.NET_ADD_AWARD)
                        .build().execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
            }
        });
        builder.setNegativeButton("取消献花", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @SuppressLint("JavascriptInterface")
    private void updateUI() {
        if (mGetBlogDetailModel.getBody().size() == 0) {
            return;
        }
        GetBlogDetailModel.BodyEntity mBodyEntity = mGetBlogDetailModel.getBody().get(0);

        blogUserId = mBodyEntity.getUser_id();
        favoriteId = mBodyEntity.getFavorite_id();
        isReplay = mBodyEntity.getReplay_flag();

        setBarTitleTx(mBodyEntity.getTitle());

        if (mBodyEntity.getGoods_id() > 0) {//是否事商品贴
            blogdetailHeadRl.setVisibility(View.GONE);
        } else {
            blogdetailHeadRl.setVisibility(View.VISIBLE);
        }

        if (mBodyEntity.getSex().equals("M")) {
            blogdetailGenderImg.setImageResource(R.mipmap.personal_man_ic);
        } else {
            blogdetailGenderImg.setImageResource(R.mipmap.personal_weman_ic);
        }

        if (mBodyEntity.getAnonymityUser() != null) {//匿名用户
            isAnonymity = true;
            anonymityImg = Common.ImageUrl + mBodyEntity.getAnonymityUser().getIcon();
            ImageLoader.getInstance().displayImage(anonymityImg
                    , blogdetailHeadImg, MyBaseApplication.getApplication().getOptionsNot());
            blogdetailNameTx.setText(mBodyEntity.getAnonymityUser().getNick_name());
            blogdetailLevelTx.setVisibility(View.GONE);
            blogdetailGenderImg.setVisibility(View.GONE);
            blogdetailVerifyImg.setVisibility(View.GONE);
            blogdetailVipTx.setVisibility(View.GONE);
            blogdetailSvipIv.setVisibility(View.GONE);
        } else {
            isAnonymity = false;
            blogdetailLevelTx.setVisibility(View.VISIBLE);
            if (mBodyEntity.getVerify_status() == 1) {
                blogdetailVerifyImg.setVisibility(View.VISIBLE);
                blogdetailGenderImg.setVisibility(View.GONE);
            } else {
                blogdetailVerifyImg.setVisibility(View.GONE);
                blogdetailGenderImg.setVisibility(View.VISIBLE);
            }
            blogdetailNameTx.setText(mBodyEntity.getUser_name());
            blogdetailLevelTx.setText("Lv " + mBodyEntity.getUserLevel());
            if (vipLevel>=3) {
                blogdetailNameTx.setTextColor(getResources().getColor(R.color.color_red));
            } else {
                blogdetailNameTx.setTextColor(getResources().getColor(R.color.color_333333));
            }
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mBodyEntity.getIcon()
                    , blogdetailHeadImg, MyBaseApplication.getApplication().getOptionsNot());
        }

        if (vipLevel>=4) {
            blogdetailVipTx.setVisibility(View.GONE);
            blogdetailSvipIv.setVisibility(View.VISIBLE);
        } else {
            blogdetailSvipIv.setVisibility(View.GONE);
            if (vipLevel==0) {
                blogdetailVipTx.setVisibility(View.GONE);
            } else {
                blogdetailVipTx.setText("VIP."+vipLevel);
                blogdetailVipTx.setVisibility(View.VISIBLE);
            }
        }

        bloglistTimeTx.setText(MyTools.convertTime(mBodyEntity.getCreate_time(), "yyyy.MM.dd HH:mm"));
        blogdetailTitleTx.setText(mBodyEntity.getTitle());

        String temp = mBodyEntity.getContent();
        //<wysqimg=  =wysqimg>
        if (temp.contains("<wysqimg=")) {
            String[] strList = temp.split("<wysqimg=");
            String mContent = "";
            int n = 0;
            for (int i = 0; i < strList.length; i++) {
                String str = "";
                String img = "";

                if (mContent != "") {
                    mContent += "<br />";
                }

                if (strList[i].contains("=wysqimg>")) {
                    img = strList[i].substring(0, strList[i].indexOf("=wysqimg>"));
                    str = strList[i].substring(strList[i].indexOf("=wysqimg>") + 9, strList[i].length());
                    mContent += "<br /><img src=\"" + Common.ImageUrl + img + "\"/>" + str;
                } else {
                    str = strList[i];
                    mContent += str;
                }

                if (!img.isEmpty()) {
                    if (imgStr != "") {
                        imgStr += ",";
                    }
                    imgStr += Common.ImageUrl + img;
                    View mImageView = LayoutInflater.from(mContext).inflate(R.layout.part_postingsdetail_imageview, null);
                    WebView mWb = (WebView) mImageView.findViewById(R.id.part_posting_wb);

                    String customHtml = getHtmlData("<br /><img src=\"" + Common.ImageUrl + img + "\"/>",n);
                    mWb.getSettings().setDefaultTextEncodingName("utf-8");
                    mWb.getSettings().setBlockNetworkImage(false);
                    mWb.getSettings().setJavaScriptEnabled(true);
                    mWb.addJavascriptInterface(new MyJSInterface(PostingsDetailActivity.this), "imagelistner");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mWb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                    } else {
                        mWb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
                    }
                    LogUtils.e("customHtml:"+customHtml);
                    mWb.loadData(customHtml, "text/html; charset=utf-8", "UTF-8");
                    blogdetailContentLl.addView(mImageView);
                    n += 1;
                }

                if (!str.isEmpty()) {
                    View mTextView = LayoutInflater.from(mContext).inflate(R.layout.part_postingsdetail_textview, null);
                    TextView mTx = (TextView) mTextView.findViewById(R.id.part_posting_tx);
                    mTx.setText(SmileUtils.getEmotionContent(mContext, mTx, str));
                    blogdetailContentLl.addView(mTextView);
                }
            }

//            String customHtml = getHtmlData(mContent);
//            blogdetailContentWb.getSettings().setDefaultTextEncodingName("utf-8");
//            blogdetailContentWb.getSettings().setBlockNetworkImage(false);
//            blogdetailContentWb.getSettings().setJavaScriptEnabled(true);
//            blogdetailContentWb.addJavascriptInterface(new MyJSInterface(PostingsDetailActivity.this), "imagelistner");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                blogdetailContentWb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            } else {
//                blogdetailContentWb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//            }
//            blogdetailContentWb.loadData(customHtml, "text/html; charset=utf-8", "UTF-8");
//            blogdetailContentLl.setVisibility(View.GONE);
//            blogdetailContentWb.setVisibility(View.VISIBLE);
        } else {
            View mTextView = LayoutInflater.from(mContext).inflate(R.layout.part_postingsdetail_textview, null);
            TextView mTx = (TextView) mTextView.findViewById(R.id.part_posting_tx);
            mTx.setText(SmileUtils.getEmotionContent(mContext, mTx, temp));
            blogdetailContentLl.addView(mTextView);
            blogdetailContentLl.setVisibility(View.VISIBLE);
            blogdetailContentWb.setVisibility(View.GONE);
        }

        mAdapter.setBlogUserId(blogUserId);
        mAdapter.setAnonymity(isAnonymity);
        mAdapter.setAnonymityImg(anonymityImg);
    }

    private void showBottomImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        if (favoriteId > 0) {
            favoriteStr = "取消收藏";
        } else {
            favoriteStr = "收藏";
        }
        String[] str;
        if (isMy) {
            str = new String[]{"分享", favoriteStr, "举报", "把TA加入黑名单", "删除"};
        } else {
            str = new String[]{"分享", favoriteStr, "举报", "把TA加入黑名单"};
        }
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//分享
                    ShareDialog.Builder builder = new ShareDialog.Builder(mContext);
                    builder.setTitle("分享");
                    builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ShareDialog dailog = builder.show();
                    dailog.setShareListener(new ShareDialog.ShareListener() {
                        @Override
                        public void onShare(int type) {
                            SHARE_MEDIA Platform = null;
                            if (type == 1) {//分享微信
                                Platform = SHARE_MEDIA.WEIXIN;
                            } else if (type == 2) {//朋友圈
                                Platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                            } else if (type == 3) {//分享新浪微博
                                Platform = SHARE_MEDIA.SINA;
                            } else if (type == 4) {//qq
                                Platform = SHARE_MEDIA.QQ;
                            } else if (type == 5) {
                                Tools.copy("午夜神器" + "\n"
                                        + MyBaseApplication.mDownLoad_URL, mContext);
                            }
                            MyBaseApplication.getApplication().setFilterLock(true);
                            ShareHelper.share(PostingsDetailActivity.this, Platform
                                    , MyBaseApplication.mDownLoad_URL, PostingsDetailActivity.this);
                        }
                    });
                } else if (which == 1) {//收藏
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    if (favoriteId > 0) {//已收藏，点击取消收藏
                        OkHttpUtils.delete().url(Common.Url_Get_BlogCollection_Not + bolgId)
                                .id(Common.NET_GET_BLOGCOLLECTION_NOT)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .tag(Common.NET_GET_BLOGCOLLECTION_NOT).build()
                                .execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
                    } else {//未收藏，点击收藏
                        OkHttpUtils.postString().url(Common.Url_Get_BlogCollection + bolgId)
                                .id(Common.NET_GET_BLOGCOLLECTION).content("{}").mediaType(Common.JSON)
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .tag(Common.NET_GET_BLOGCOLLECTION).build()
                                .execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
                    }
                } else if (which == 2) {//举报
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    startActivity(ReportActivity.buildIntent(mContext, (long) bolgId, 2));
                } else if (which == 3) {//把TA加入黑名单
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    if (mGetBlogDetailModel.getBody().size() > 0
                            && MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id()
                            == mGetBlogDetailModel.getBody().get(0).getUser_id()) {
                        showToast("不能将自己加入黑名单");
                        return;
                    }
                    OkHttpUtils.postString().url(Common.Url_Add_BlackList)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .content("{\"black_user_id\":" + mGetBlogDetailModel.getBody().get(0).getUser_id() + "}")
                            .mediaType(Common.JSON).id(Common.NET_ADD_BLACKLIST).tag(Common.NET_ADD_BLACKLIST)
                            .build().execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
                } else if (which == 4) {//删除
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    OkHttpUtils.postString().url(Common.Url_Delete_Post + bolgId).mediaType(Common.JSON)
                            .content("{}")
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .id(Common.NET_DELETE_POST).tag(Common.NET_DELETE_POST)
                            .build().execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
                }
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isFastDoubleClick()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // js通信接口
    public class MyJSInterface {

        private Context context;

        public MyJSInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(int num) {
            LogUtils.e("imgStr:" + imgStr + ",num:" + num);
            Intent intent = new Intent();
            intent.putExtra("image", imgStr);
            intent.putExtra("num", num);
            intent.setClass(context, PictureBrowsingActivity.class);
            context.startActivity(intent);
        }
    }

    private String getHtmlData(String bodyHTML, int m) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{margin:0;padding:0;}img{max-width: 100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + getJS(m) + "</body></html>";
    }

    private String getJS(int num) {
        return "<script>document.querySelectorAll('img')[0].onclick = function() {\n" +
                "    imagelistner.openImage("+num+");\n" +
                "}</script>";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGDETAIL);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGDETAIL_COMMENTLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_CATEGORY_DETAIL);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_COMMENT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_LIKE);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_AWARDLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BLACKLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_AWARD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_DELETE_POST);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_postingsdetail_comment_like_tv:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                OkHttpUtils.postString().url(Common.Url_Add_Like + "1/" + mAdapter.getItem(position).getBlog_comment_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .content("{}").mediaType(Common.JSON).id(Common.NET_ADD_LIKE).tag(Common.NET_ADD_LIKE)
                        .build().execute(new MyStringCallback(mContext, PostingsDetailActivity.this, true));
                break;
            case R.id.item_postingsdetail_comment_head_rl:
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getUser_id()));
                break;
        }
    }

    @OnClick({R.id.postings_totop_iv, R.id.blogdetail_send_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postings_totop_iv:
                mListView.setSelection(0);
                break;
            case R.id.blogdetail_send_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                sendMessage(view);
                break;
        }
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
