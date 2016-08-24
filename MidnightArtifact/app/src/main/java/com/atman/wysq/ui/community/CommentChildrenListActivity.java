package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ChildrenCommentAdapter;
import com.atman.wysq.model.response.GetChildrenCommentModel;
import com.atman.wysq.model.response.GetPostingsDetailsCommentListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.MyCleanEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/2 17:26
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CommentChildrenListActivity extends MyBaseActivity implements AdapterInterface,View.OnTouchListener {

    @Bind(R.id.blogdetail_comment_lv)
    PullToRefreshListView blogdetailCommentLv;
    @Bind(R.id.postings_totop_iv)
    ImageView postingsTotopIv;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_send_bt)
    Button blogdetailSendBt;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;

    private Context mContext = CommentChildrenListActivity.this;

    private int id;
    private int verifyState;
    private int level;
    private int page = 1;
    private int blog_id;
    private int isReplay = 0;
    private boolean isLast = false;
    private String headUrl;
    private String name;
    private String sex;
    private String content;
    private String anonymityImg;
    private long time;
    private long ueseID;
    private long blogUserId;
    private boolean isAnonymity;

    private GetChildrenCommentModel mGetChildrenCommentModel;
    private ChildrenCommentAdapter mAdapter;

    private View headView;
    private CustomImageView childrencommentHeadImg;
    private CustomImageView childrencommentVerifyImg;
    private ImageView childrencommentGenderImg;
    private TextView childrencommentNameTx;
    private TextView childrencommentLevelTx;
    private TextView childrencommentTimeTx;
    private TextView childrencommentHostTx;
    private TextView childrencommentCommentTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_commentchildrenlist);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static Intent buildIntent(Context context, int blog_id, int id, String headUrl, int verifyState, String name
            , String sex, int level, long time, long ueseID, String content, long blogUserId, boolean isAnonymity
            , String anonymityImg, int isReplay) {
        Intent intent = new Intent(context, CommentChildrenListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("blog_id", blog_id);
        intent.putExtra("verifyState", verifyState);
        intent.putExtra("headUrl", headUrl);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("level", level);
        intent.putExtra("time", time);
        intent.putExtra("ueseID", ueseID);
        intent.putExtra("content", content);
        intent.putExtra("isAnonymity", isAnonymity);
        intent.putExtra("blogUserId", blogUserId);
        intent.putExtra("anonymityImg", anonymityImg);
        intent.putExtra("isReplay", isReplay);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        id = getIntent().getIntExtra("id", -1);
        verifyState = getIntent().getIntExtra("verifyState", 0);
        isReplay = getIntent().getIntExtra("isReplay", 0);
        level = getIntent().getIntExtra("level", 0);
        headUrl = getIntent().getStringExtra("headUrl");
        blog_id = getIntent().getIntExtra("blog_id", -1);
        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");
        anonymityImg = getIntent().getStringExtra("anonymityImg");
        content = getIntent().getStringExtra("content");
        time = getIntent().getLongExtra("time", 0);
        ueseID = getIntent().getLongExtra("ueseID", 0);
        blogUserId = getIntent().getLongExtra("blogUserId", 0);
        isAnonymity = getIntent().getBooleanExtra("isAnonymity", false);
        LogUtils.e(">>>id:" + id+",blogUserId:"+blogUserId+",isAnonymity:"+isAnonymity);

        setBarTitleTx("回复详情");

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_childrencomment_head_view, null);
        childrencommentHeadImg = (CustomImageView) headView.findViewById(R.id.childrencomment_head_img);
        childrencommentVerifyImg = (CustomImageView) headView.findViewById(R.id.childrencomment_verify_img);
        childrencommentGenderImg = (ImageView) headView.findViewById(R.id.childrencomment_gender_img);
        childrencommentNameTx = (TextView) headView.findViewById(R.id.childrencomment_name_tx);
        childrencommentLevelTx = (TextView) headView.findViewById(R.id.childrencomment_level_tx);
        childrencommentTimeTx = (TextView) headView.findViewById(R.id.childrencomment_time_tx);
        childrencommentHostTx = (TextView) headView.findViewById(R.id.childrencomment_host_tx);
        childrencommentCommentTx = (TextView) headView.findViewById(R.id.blogdetail_comment_tx);

        ImageLoader.getInstance().displayImage(Common.ImageUrl+headUrl, childrencommentHeadImg, MyBaseApplication.getApp().getOptionsNot());
        if (verifyState == 1) {
            childrencommentVerifyImg.setVisibility(View.VISIBLE);
            childrencommentGenderImg.setVisibility(View.GONE);
        } else {
            childrencommentVerifyImg.setVisibility(View.GONE);
            childrencommentGenderImg.setVisibility(View.VISIBLE);
            if (sex.equals("M")) {
                childrencommentGenderImg.setImageResource(R.mipmap.personal_man_ic);
            } else {
                childrencommentGenderImg.setImageResource(R.mipmap.personal_weman_ic);
            }
        }
        childrencommentLevelTx.setText("Lv " + level);
        childrencommentNameTx.setText(name);
        childrencommentCommentTx.setText(content);
        childrencommentTimeTx.setText(MyTools.convertTime(time, "yyyy.MM.dd HH:mm"));

        initListView();

        if (blogUserId == ueseID) {
            if (isAnonymity) {
                childrencommentLevelTx.setVisibility(View.GONE);
                childrencommentGenderImg.setVisibility(View.GONE);
                childrencommentVerifyImg.setVisibility(View.GONE);
                childrencommentNameTx.setText("匿名用户");
                ImageLoader.getInstance().displayImage(anonymityImg, childrencommentHeadImg
                        , MyBaseApplication.getApp().getOptionsNot());
            } else {
                childrencommentNameTx.setText(name);
                ImageLoader.getInstance().displayImage(Common.ImageUrl+headUrl, childrencommentHeadImg
                        , MyBaseApplication.getApp().getOptionsNot());
            }
            childrencommentHostTx.setVisibility(View.VISIBLE);
        } else {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+headUrl, childrencommentHeadImg
                    , MyBaseApplication.getApp().getOptionsNot());
            childrencommentHostTx.setVisibility(View.INVISIBLE);
        }

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
            OkHttpUtils.postString().url(Common.Url_Add_Comment).mediaType(Common.JSON)
                    .content("{\"blog_id\":\""+blog_id+"\",\"content\":\""+str+"\",\"comment_id\":\""+id+"\"}")
                    .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                    .id(Common.NET_ADD_COMMENT).tag(Common.NET_ADD_COMMENT)
                    .build().execute(new MyStringCallback(mContext, CommentChildrenListActivity.this, true));
        }
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
        mAdapter = new ChildrenCommentAdapter(mContext, blogUserId, isAnonymity, anonymityImg, this);
        blogdetailCommentLv.getRefreshableView().addHeaderView(headView);
        blogdetailCommentLv.setAdapter(mAdapter);
        blogdetailCommentLv.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    cancelIM(view);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = blogdetailCommentLv.getRefreshableView().getChildAt(0);
                if (c == null) {
                    return;
                }
                int firstVisiblePosition = blogdetailCommentLv.getRefreshableView().getFirstVisiblePosition();
                int top = c.getTop();
                if ((headView.getHeight() >= getmHight() && (-top >= getmHight() || firstVisiblePosition > 1))
                        || (headView.getHeight() < getmHight()) && (headView.getHeight() + firstVisiblePosition * c.getHeight()) >= getmHight()) {
                    postingsTotopIv.setVisibility(View.VISIBLE);
                } else {
                    postingsTotopIv.setVisibility(View.GONE);
                }
            }
        });
        postingsTotopIv.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_SubComment_List + id + "/" + page)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .id(Common.NET_SUBCOMMENT_LIST).tag(Common.NET_SUBCOMMENT_LIST).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SUBCOMMENT_LIST) {
            mGetChildrenCommentModel = mGson.fromJson(data, GetChildrenCommentModel.class);
            if (mGetChildrenCommentModel.getBody() == null
                    || mGetChildrenCommentModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                isLast = true;
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, blogdetailCommentLv);
            } else {
                if (mGetChildrenCommentModel.getBody().size()<20) {
                    isLast = true;
                } else {
                    isLast = false;
                }
                onLoad(PullToRefreshBase.Mode.BOTH, blogdetailCommentLv);
                mAdapter.addBody(mGetChildrenCommentModel.getBody());
            }
        } else if (id == Common.NET_ADD_COMMENT) {
            if (isLast) {
                GetChildrenCommentModel.BodyEntity body = new GetChildrenCommentModel.BodyEntity();
                body.setContent(blogdetailAddcommentEt.getText().toString().trim());
                body.setCreate_time(System.currentTimeMillis());
                body.setIcon(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getIcon());
                body.setSex(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getSex());
                body.setVerify_status(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getVerify_status());
                body.setUserLevel(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getUserLevel());
                body.setUser_name(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getNick_name());
                body.setUser_id(MyBaseApplication.mGetUserInfoModel.getBody().getUserExt().getUser_id());
                mAdapter.addBody(body);
            }
            blogdetailAddcommentEt.setText("");
            showToast("评论成功");
            if (isReplay == 0) {
                Toast.makeText(mContext,"+1经验值",Toast.LENGTH_LONG).show();
                isReplay = 1;
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SUBCOMMENT_LIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_COMMENT);
    }

    @OnClick({R.id.postings_totop_iv, R.id.blogdetail_send_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postings_totop_iv:
                blogdetailCommentLv.getRefreshableView().setSelection(0);
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
    public void onItemClick(View view, int position) {

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
}
