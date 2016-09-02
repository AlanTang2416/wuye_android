package com.atman.wysq.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MallCategoryListAdapter;
import com.atman.wysq.model.response.MallGetCategoryResponseModel;
import com.atman.wysq.model.response.MallGetTwoCategoryResponseModel;
import com.atman.wysq.model.response.MallTopResponseModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.order.MyOrderListActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.adview.ADInfo;
import com.base.baselibs.widget.adview.CycleViewPager;
import com.base.baselibs.widget.adview.ViewFactory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 商城
 * 作者 tangbingliang
 * 时间 16/7/1 18:09
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MallFragment extends MyBaseFragment implements MallCategoryListAdapter.CategoryAdapterInterface {

    @Bind(R.id.fragment_bar_title_iv)
    ImageView fragmentBarTitleIv;
    LinearLayout mallTopAdLl;
    @Bind(R.id.mall_category_listview)
    ListView mallCategoryListview;
    @Bind(R.id.fragment_bar_right_tx)
    TextView fragmentBarRightTx;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;

    private String[] imageUrls;
    private MallTopResponseModel mMallTopResponseModel;
    private MallGetCategoryResponseModel mMallGetCategoryResponseModel;
    private MallCategoryListAdapter mAdapter;

    private View categoryHeadview;

    private boolean isError = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, null);
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
        fragmentBarTitleIv.setImageResource(R.mipmap.top_mall_ic);

        categoryHeadview = LayoutInflater.from(getActivity()).inflate(R.layout.part_category_head_view, null);
        mallTopAdLl = (LinearLayout) categoryHeadview.findViewById(R.id.mall_top_ad_ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 168 / 413);
        mallTopAdLl.setLayoutParams(params);
        mallCategoryListview.addHeaderView(categoryHeadview);
        fragmentBarRightTx.setVisibility(View.INVISIBLE);
        fragmentBarRightTx.setText("订单");
        fragmentBarRightTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    //需要登陆状态，跳转到登陆界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                }
            }
        });

    }

    private void initialize() {

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

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
        cycleViewPager.setData(views, infos, mAdCycleViewListener);

        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorRight();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
            }
            if (position<0) {
                position = 0;
            }
            UiHelper.toActivity(getActivity(), mMallTopResponseModel.getBody().get(position), isLogin());

        }

    };

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
            OkHttpUtils.get().url(Common.Url_AdList + 2).addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_AD_LIST).id(Common.NET_AD_LIST).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
            OkHttpUtils.get().url(Common.Url_Category_List).addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .tag(Common.NET_CATEGORY_LIST).id(Common.NET_CATEGORY_LIST).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_AD_LIST) {
            mMallTopResponseModel = mGson.fromJson(data, MallTopResponseModel.class);
            imageUrls = new String[mMallTopResponseModel.getBody().size()];
            for (int i = 0; i < mMallTopResponseModel.getBody().size(); i++) {
                imageUrls[i] = Common.ImageUrl + mMallTopResponseModel.getBody().get(i).getAd_pic().replace("<wysqimg=","").replace("=wysqimg>","");
            }
            initialize();
        } else if (id == Common.NET_CATEGORY_LIST) {
            mMallGetCategoryResponseModel = mGson.fromJson(data, MallGetCategoryResponseModel.class);
            initListView();
            mAdapter.clearTwoCategory();
            for (int i = 0; i < mMallGetCategoryResponseModel.getBody().size(); i++) {
                OkHttpUtils.get().url(Common.Url_Two_Category_List + mMallGetCategoryResponseModel.getBody().get(i).getGoods_category_id())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_TWO_CATEGORY_LIST).id(Common.NET_TWO_CATEGORY_LIST).build()
                        .execute(new MyStringCallback(getActivity(), this, true));
            }
        } else if (id == Common.NET_TWO_CATEGORY_LIST) {
            MallGetTwoCategoryResponseModel mMallGetTwoCategoryResponseModel
                    = mGson.fromJson(data, MallGetTwoCategoryResponseModel.class);
            mAdapter.addTwoCategory(mMallGetTwoCategoryResponseModel);
        }
    }

    private void initListView() {
        mAdapter = new MallCategoryListAdapter(getActivity(), getmWidth(), mMallGetCategoryResponseModel.getBody(), this);
        mallCategoryListview.setAdapter(mAdapter);
        mallCategoryListview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
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
        OkHttpUtils.getInstance().cancelTag(Common.NET_AD_LIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CATEGORY_LIST);
    }

    @Override
    public void onItemClick(View view, int id, String name) {
        startActivity(TwoLevelCategoryListActivity.buildIntent(getActivity(), id, name, false));
    }
}
