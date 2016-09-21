package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
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
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.NoSwipeViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/27 15:04
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostingsByClassificationActivity extends MyBaseActivity {

    @Bind(R.id.postings_tab_new_rt)
    RadioButton postingsTabNewRt;
    @Bind(R.id.postings_tab_hot_rt)
    RadioButton postingsTabHotRt;
    @Bind(R.id.postings_tab_boutique_rt)
    RadioButton postingsTabBoutiqueRt;
    @Bind(R.id.postings_top_tab_rg)
    RadioGroup postingsTopTabRg;
    @Bind(R.id.postings_viewpager)
    NoSwipeViewPager postingsViewpager;

    private Context mContext = PostingsByClassificationActivity.this;

    private String title;
    private String canPost;
    private int id;
    private int[] typeId = {3, 0, 2}; //0：热门 2:精品 3:最新
    private final String[] TAG = {"new", "hot", "highly"};
    private Adapter adapter;
    private Fragment fg;

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
        initViewpager();
        initBottomBar();
    }

    private void initViewpager() {
        postingsViewpager.setPagingEnabled(true);//是否支持手势滑动
        adapter = new Adapter(getSupportFragmentManager());

        for (int i=0;i<3;i++) {
            PostingsByClassificationFragment oneFragment = new PostingsByClassificationFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            bundle.putInt("typeId", typeId[i]);
            oneFragment.setArguments(bundle);
            adapter.addFragment(oneFragment, TAG[i]);
        }

        postingsViewpager.setOffscreenPageLimit(3);
        postingsViewpager.setAdapter(adapter);
        postingsViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0) {
                    postingsTabNewRt.performClick();
                } else if (position==1) {
                    postingsTabHotRt.performClick();
                } else if (position==2) {
                    postingsTabBoutiqueRt.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectItem(1);
    }

    private void canToPost() {
        boolean isCan = false;
        if (canPost.equals("111")) {//所有用户都可以
            isCan = true;
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("100")) {//男性用户才可以
            if (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("只有男性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("010")) {//女性用户才可以
            if (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")) {
                isCan = true;
            } else {
                showToast("只有女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("001")) {//认证女性用户才可以
            if (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status() == 1) {
                isCan = true;
            } else {
                showToast("已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("101")) {//男用户和认证女性用户才可以
            if ((MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status() == 1)
                    || MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("男性用户和已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("110")) {//男性用户和女性用户才可以发帖
            if (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")
                    || MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
                isCan = true;
            } else {
                showToast("男性用户和女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("011")) {//女性用户和女性用户才可以发帖
            if ((MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")
                    && MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status() == 1)
                    || MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")) {
                isCan = true;
            } else {
                showToast("女性用户和已认证女性用户才可以发帖");
            }
        } else if (MyBaseApplication.mGetMyUserIndexModel != null && canPost.equals("000")) {//管理员才可以发帖
            if (MyBaseApplication.mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getType() == 2) {
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
                switch (checkedId) {
                    case R.id.postings_tab_new_rt:
                        selectItem(0);
                        break;
                    case R.id.postings_tab_hot_rt:
                        selectItem(1);
                        break;
                    case R.id.postings_tab_boutique_rt:
                        selectItem(2);
                        break;
                }
            }
        });
    }

    private void selectItem(int i) {
        postingsViewpager.setCurrentItem(i, true);
        fg = adapter.getItem(i);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
