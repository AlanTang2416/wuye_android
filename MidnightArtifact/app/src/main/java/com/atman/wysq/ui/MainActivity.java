package com.atman.wysq.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyFragmentAdapter;
import com.atman.wysq.model.event.YunXinAuthOutEvent;
import com.atman.wysq.model.response.CheckVersionModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.WebPageActivity;
import com.atman.wysq.ui.community.CommunityFragment;
import com.atman.wysq.ui.community.PostingsDetailActivity;
import com.atman.wysq.ui.discover.DiscoverFragment;
import com.atman.wysq.ui.mall.GoodsDetailActivity;
import com.atman.wysq.ui.mall.MallFragment;
import com.atman.wysq.ui.mall.TwoLevelCategoryListActivity;
import com.atman.wysq.ui.message.MessageFragment;
import com.atman.wysq.ui.personal.PersonalFragment;
import com.atman.wysq.ui.personal.TaskListActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.widget.face.FaceConversionUtil;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.NoSwipeViewPager;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class MainActivity extends MyBaseActivity {

    @Bind(R.id.tab_community)
    RadioButton tabCommunity;
    @Bind(R.id.tab_mall)
    RadioButton tabMall;
    @Bind(R.id.tab_message)
    RadioButton tabMessage;
    @Bind(R.id.tab_discover)
    RadioButton tabDiscover;
    @Bind(R.id.tab_personal)
    RadioButton tabPersonal;
    @Bind(R.id.tabs_rg)
    RadioGroup tabsRg;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.main_viewpager)
    NoSwipeViewPager viewpager;
    @Bind(R.id.contentview)
    RelativeLayout contentview;
    @Bind(R.id.main_content)
    LinearLayout mainContent;

    private Fragment fg;
    private MyFragmentAdapter adapter;
    private final String COMMUNITY_TAG = "community";
    private final String MALL_TAG = "mall";
    private final String MESSAGE_TAG = "message";
    private final String DISCOVER_TAG = "discover";
    private final String PERSONAL_TAG = "personal";

    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableLoginCheck();
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra("isToWeb",false)) {
            LogUtils.e("MyBaseApplication.mWEB_TYPE:"+MyBaseApplication.mWEB_TYPE);
            if (MyBaseApplication.mWEB_TYPE.equals("1")) {//URL
                startActivity(WebPageActivity.buildIntent(MainActivity.this, MyBaseApplication.mWEB_URL, ""));
            } else if (MyBaseApplication.mWEB_TYPE.equals("2")) {//专辑

            } else if (MyBaseApplication.mWEB_TYPE.equals("3")) {//任务
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(new Intent(mContext, TaskListActivity.class));
                }
            } else if (MyBaseApplication.mWEB_TYPE.equals("4")) {//商品详情
                startActivity(GoodsDetailActivity.buildIntent(mContext, Integer.parseInt(MyBaseApplication.mWEB_ID)));
            } else if (MyBaseApplication.mWEB_TYPE.equals("5")) {//商品列表
                startActivity(TwoLevelCategoryListActivity.buildIntent(mContext,
                        Integer.parseInt(MyBaseApplication.mWEB_ID), "", false));
            } else if (MyBaseApplication.mWEB_TYPE.equals("6")) {//帖子
                startActivity(PostingsDetailActivity.buildIntent(mContext, ""
                        , Integer.parseInt(MyBaseApplication.mWEB_ID), false));
            } else if (MyBaseApplication.mWEB_TYPE.equals("7")) {//午夜Url

            } else if (MyBaseApplication.mWEB_TYPE.equals("8")) {//众筹

            } else if (MyBaseApplication.mWEB_TYPE.equals("9")) {//心愿墙

            } else if (MyBaseApplication.mWEB_TYPE.equals("10")) {//根据type取商品
                startActivity(TwoLevelCategoryListActivity.buildIntent(mContext,
                        Integer.parseInt(MyBaseApplication.mWEB_ID), "VIP", true));
            }
        }

        OkHttpUtils.get().url(Common.Url_Get_Version+"?version="+MyBaseApplication.mVersionName.replace("v",""))
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .id(Common.NET_GET_VERSION).tag(Common.NET_GET_VERSION).build().execute(new MyStringCallback(mContext, this, false));
        EventBus.getDefault().register(this);
    }

    //在注册了的Activity中,声明处理事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(YunXinAuthOutEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
        builder.setTitle("账号异常");
        builder.setMessage("您的账号已在别处登录，请退出登录，如非您本人操作，请尽快修改您的密码！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    public static Intent buildIntent(Context context, boolean isToWeb){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("isToWeb", isToWeb);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
            }
        }).start();
        hideTitleBar();
        initViewpager();
        initBottomBar();
        setSwipeBackEnable(false);
    }

    private void initBottomBar() {
        tabsRg = (RadioGroup) findViewById(R.id.tabs_rg);
        tabsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_message:
                        viewpager.setCurrentItem(0,false);
                        fg = adapter.getItem(0);
                        break;
                    case R.id.tab_community:
                        viewpager.setCurrentItem(1,false);
                        fg = adapter.getItem(1);
                        break;
                    case R.id.tab_mall:
                        viewpager.setCurrentItem(2,false);
                        fg = adapter.getItem(2);
                        break;
                    case R.id.tab_discover:
                        viewpager.setCurrentItem(3,false);
                        fg = adapter.getItem(3);
                        break;
                    case R.id.tab_personal:
                        viewpager.setCurrentItem(4,false);
                        fg = adapter.getItem(4);
                        break;
                }
            }
        });

        //挨着给每个RadioButton加入drawable限制边距以控制显示大小
        RadioButton[] bt = {tabMessage, tabCommunity,tabMall,tabDiscover,tabPersonal};
        for (int i=0;i<bt.length;i++) {
            Drawable[] drs = bt[i].getCompoundDrawables();
            Rect r = new Rect(0, 0, drs[1].getMinimumWidth() + 20, drs[1].getMinimumHeight() + 20);
            drs[1].setBounds(r);
            bt[i].setCompoundDrawables(null,drs[1],null,null);
        }
    }

    private void initViewpager() {
        viewpager.setPagingEnabled(false);//是否支持手势滑动
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new MessageFragment(), MESSAGE_TAG);
        adapter.addFragment(new CommunityFragment(), COMMUNITY_TAG);
        adapter.addFragment(new MallFragment(), MALL_TAG);
        adapter.addFragment(new DiscoverFragment(), DISCOVER_TAG);
        adapter.addFragment(new PersonalFragment(), PERSONAL_TAG);
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);
        fg = adapter.getItem(0);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabsRg.getChildAt(position).performClick();
                fg = adapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_VERSION) {
            final CheckVersionModel mCheckVersionModel = mGson.fromJson(data, CheckVersionModel.class);
            if (mCheckVersionModel.getResult().equals("1") && mCheckVersionModel.getBody()!=null) {
                PromptDialog.Builder builder = new PromptDialog.Builder(MainActivity.this);
                builder.setMessage(mCheckVersionModel.getBody().getWarn());
                builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final File updateFile = createFile(MyBaseApplication.mVersionName);
                        if (updateFile != null) {
                            /**调用系统浏览器在页面中下载*/
                            Uri uri = Uri.parse(mCheckVersionModel.getBody().getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            showToast("SD卡路径错误，无法下载");
                        }
                    }
                });
                if (mCheckVersionModel.getBody().getForce().equals("0")) {
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    builder.setCancelable(false);
                }
                builder.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*然后在碎片中调用重写的onActivityResult方法*/
        fg.onActivityResult(requestCode, resultCode, data);
    }
}
