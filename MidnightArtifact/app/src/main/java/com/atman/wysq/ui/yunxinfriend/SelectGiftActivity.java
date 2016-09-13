package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.personal.RechargeActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SortComparator;
import com.atman.wysq.widget.face.FaceRelativeLayout;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.NoSwipeViewPager;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/8 10:09
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SelectGiftActivity extends MyBaseActivity {

    @Bind(R.id.selectgift_top_view)
    View selectgiftTopView;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_send_bt)
    Button blogdetailSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.main_viewpager)
    NoSwipeViewPager mainViewpager;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    com.atman.wysq.widget.face.FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.selectgift_mycion_tx)
    TextView selectgiftMycionTx;
    @Bind(R.id.gift_one_ll)
    LinearLayout giftOneLl;
    @Bind(R.id.gift_dot_ll)
    LinearLayout giftDotLl;
    @Bind(R.id.gift_ll)
    RelativeLayout giftLl;

    private Context mContext = SelectGiftActivity.this;
    private List<GiftListModel.BodyEntity> mGiftList = new ArrayList<>();
    private GiftListModel mGiftListModel;
    private Adapter adapter;
    private List<ImageView> pointViews;
    private long myCion;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgift);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
    }

    public static Intent buildIntent(Context context, String id){
        Intent intent = new Intent(context, SelectGiftActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        id = getIntent().getStringExtra("id");

        hideTitleBar();
        getRootContentLl().setBackgroundResource(R.color.color_gift_all);
    }

    private void initViewpager() {
        mainViewpager.setPagingEnabled(true);//是否支持手势滑动
        int num = mGiftList.size();
        int n = num / 6;
        if (num % 6 != 0) {
            n = n + 1;
        }
        LogUtils.e("n:" + n);
        adapter = new Adapter(getSupportFragmentManager());
        for (int i = 0; i < n; i++) {
            SelectGiftFragment fragment = new SelectGiftFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putInt("page", i);
            bundle.putSerializable("data", mGiftListModel);
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, i + "");
        }
        mainViewpager.setOffscreenPageLimit(n);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        InitPoint(n);
    }

    private void selectPoint(int position) {
        for (int i = 0; i < pointViews.size(); i++) {
            if (position == i) {
                pointViews.get(i).setBackgroundResource(R.mipmap.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.mipmap.d1);
            }
        }
    }

    /**
     * 初始化游标
     */
    private void InitPoint(int a) {
        pointViews = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < a; i++) {
            imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.mipmap.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            layoutParams.width = 8;
            layoutParams.height = 8;
            giftDotLl.addView(imageView, layoutParams);
            pointViews.add(imageView);
        }
        selectPoint(0);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_GiftList).id(Common.NET_GET_GIFTLIST)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GIFTLIST).build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCion = MyBaseApplication.getApplication().mGetUserIndexModel
                .getBody().getUserDetailBean().getUserExt().getGold_coin();
        selectgiftMycionTx.setText("金币余额：" + myCion);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GIFTLIST) {
            mGiftListModel = mGson.fromJson(data, GiftListModel.class);
            mGiftList = mGiftListModel.getBody();
            Comparator comp = new SortComparator();
            Collections.sort(mGiftList, comp);
            initViewpager();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GIFTLIST);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    @OnClick({R.id.selectgift_top_view, R.id.blogdetail_send_bt,R.id.selectgift_cionmart_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectgift_top_view:
                finish();
                break;
            case R.id.blogdetail_send_bt:
                break;
            case R.id.selectgift_cionmart_tx:
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(RechargeActivity.buildIntent(mContext
                            , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getGold_coin()
                            , MyBaseApplication.getApplication().mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin()));
                }
                break;
        }
    }

    public void backResuilt (String url, String text) {
        Intent mIntent = new Intent();
        mIntent.putExtra("url",url);
        if (blogdetailAddcommentEt.getText().toString().trim().isEmpty()) {
            mIntent.putExtra("text", text);
        } else {
            mIntent.putExtra("text", blogdetailAddcommentEt.getText().toString().trim());
        }
        setResult(RESULT_OK,mIntent);
        finish();
    }

    public boolean isFaceLl() {
        if (llFacechoose.getVisibility() == View.VISIBLE) {
            llFacechoose.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    public void isInput(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
