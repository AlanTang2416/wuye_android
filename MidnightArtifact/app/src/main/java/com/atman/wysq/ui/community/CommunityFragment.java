package com.atman.wysq.ui.community;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CommunityBlogBoardAdapter;
import com.atman.wysq.model.response.GetBlogBoardModel;
import com.atman.wysq.model.response.GetMyCollectionModel;
import com.atman.wysq.model.response.MallTopResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 社区
 * 作者 tangbingliang
 * 时间 16/7/1 18:08
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class CommunityFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    @Bind(R.id.community_listview)
    ListView communityListview;
    @Bind(R.id.fragment_bar_right_iv)
    ImageView fragmentBarRightIv;
    @Bind(R.id.fragment_bar_right_rl)
    RelativeLayout fragmentBarRightRl;

    private boolean isError = true;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;

    private String[] imageUrls;
    private MallTopResponseModel mMallTopResponseModel;
    private GetBlogBoardModel mGetBlogBoardModel;

    private CommunityBlogBoardAdapter mAdapter;

    private View communityHeadview;
    private LinearLayout communityTopAdLl;
    private TextView topWinSecretreplyTx;
    private TextView topWinMysecretTx;
    private TextView topWinMycollectionTx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, null);
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
        fragmentBarTitleIv.setImageResource(R.mipmap.top_community_ic);

        fragmentBarRightIv.setVisibility(View.VISIBLE);
        if (MyBaseApplication.isUnRead) {
            fragmentBarRightIv.setImageResource(R.mipmap.plaza_icon_post_new);
        } else {
            fragmentBarRightIv.setImageResource(R.mipmap.plaza_icon_post);
        }
        fragmentBarRightRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopPopWin();
            }
        });

    }

    private void showTopPopWin() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_bar_popwin_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT
                ,RelativeLayout.LayoutParams.MATCH_PARENT);
        topWinSecretreplyTx = (TextView) view.findViewById(R.id.top_win_secretreply_tx);
        Drawable drawable = null;
        if (MyBaseApplication.isUnRead) {
            drawable = getActivity().getResources().getDrawable(R.mipmap.plaza_icon_more_reply_new);
        } else {
            drawable = getActivity().getResources().getDrawable(R.mipmap.plaza_icon_more_reply);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        topWinSecretreplyTx.setCompoundDrawables(null, drawable, null, null);
        topWinSecretreplyTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!isLogin()) {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), ReplyListActivity.class));
                }
            }
        });
        topWinMysecretTx = (TextView) view.findViewById(R.id.top_win_mysecret_tx);
        topWinMysecretTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!isLogin()) {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MySecretListActivity.class));
                }
            }
        });
        topWinMycollectionTx = (TextView) view.findViewById(R.id.top_win_mycollection_tx);
        topWinMycollectionTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!isLogin()) {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MycollectionActivity.class));
                }
            }
        });
        view.findViewById(R.id.top_win_iv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(fragmentBarRightIv);
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null && isError) {
            isError = false;
            OkHttpUtils.get().url(Common.Url_AdList + 4).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_AD_LIST).id(Common.NET_AD_LIST).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
            OkHttpUtils.get().url(Common.Url_Get_BlogBoard).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_GET_BLOGBOARD).id(Common.NET_GET_BLOGBOARD).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
        }
    }

    private void initialize() {

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.community_fragment_cycle_viewpager_content);

        infos.clear();
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        views.clear();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            ImageView img = ViewFactory.getImageView(getActivity(), infos.get(i).getUrl());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(img);
        }
        // 将第一个ImageView添加进来
        ImageView imageView = ViewFactory.getImageView(getActivity(), infos.get(0).getUrl());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views.add(imageView);

        // 设置循环，在调用setData方法前调用
        if (cycleViewPager==null) {
            if (getUserVisibleHint()) {
                OkHttpUtils.get().url(Common.Url_AdList + 4).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_AD_LIST).id(Common.NET_AD_LIST).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
            }
            return;
        }
        if (infos.size()<=1) {
            cycleViewPager.setCycle(false);
            cycleViewPager.setScrollable(false);
            cycleViewPager.setWheel(false);
        } else {
            cycleViewPager.setCycle(true);
            cycleViewPager.setScrollable(true);
            cycleViewPager.setWheel(true);
            cycleViewPager.setTime(5000);
        }

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
            }
            UiHelper.toActivity(getActivity(), mMallTopResponseModel.getBody().get(position), isLogin());
        }

    };

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_AD_LIST) {
            mMallTopResponseModel = mGson.fromJson(data, MallTopResponseModel.class);
            imageUrls = new String[mMallTopResponseModel.getBody().size()];
            for (int i = 0; i < mMallTopResponseModel.getBody().size(); i++) {
                imageUrls[i] = Common.ImageUrl + mMallTopResponseModel.getBody().get(i).getAd_pic();
            }
            initialize();
        } else if (id == Common.NET_GET_BLOGBOARD) {
            mGetBlogBoardModel = mGson.fromJson(data, GetBlogBoardModel.class);
            communityHeadview = LayoutInflater.from(getActivity()).inflate(R.layout.part_community_head_view, null);
            communityTopAdLl = (LinearLayout) communityHeadview.findViewById(R.id.community_top_ad_ll);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                    getmWidth() * 168 / 413);
            communityTopAdLl.setLayoutParams(params);
            communityListview.addHeaderView(communityHeadview);
            mAdapter = new CommunityBlogBoardAdapter(getActivity(), mGetBlogBoardModel.getBody(), this);
            communityListview.setAdapter(mAdapter);
        } else if (id == Common.NET_GET_USERCOMMENT) {
            GetMyCollectionModel mGetMyCollectionModel = mGson.fromJson(data, GetMyCollectionModel.class);
            for (int i=0;i<mGetMyCollectionModel.getBody().size();i++) {
                if (mGetMyCollectionModel.getBody().get(i).getUnread_flag()==1) {
                    MyBaseApplication.isUnRead = true;
                    fragmentBarRightIv.setImageResource(R.mipmap.plaza_icon_post_new);
                    break;
                }
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        isError = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGBOARD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_AD_LIST);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(PostingsByClassificationActivity.buildIntent(getActivity()
                , mGetBlogBoardModel.getBody().get(position).getTitle()
                , mGetBlogBoardModel.getBody().get(position).getId()
                , mGetBlogBoardModel.getBody().get(position).getCan_post()));
    }
}
