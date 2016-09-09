package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetUserIndexModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.community.ReportActivity;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.ShareHelper;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.widget.ShareDialog;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.RoundImageView;
import com.base.baselibs.widget.pullzoom.PullZoomScrollVIew;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/26 11:45
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class OtherPersonalActivity extends MyBaseActivity implements View.OnClickListener{

    @Bind(R.id.otherpersonal_scrollview)
    PullZoomScrollVIew otherpersonalScrollview;

    private Context mContext = OtherPersonalActivity.this;
    private long id;
    private GetUserIndexModel mGetUserIndexModel;

    private LinearLayout otherpersonalBackLl;
    private LinearLayout otherpersonalMoreLl;
    private LinearLayout otherpersonalDynamicLl;
    private LinearLayout otherpersonalVisitorLl;
    private LinearLayout otherpersonalFriendsLl;
    private ImageView otherpersonalHeadIv;
    private ImageView otherpersonalGenderIv;
    private ImageView otherpersonalHeadVerifyImg;
    private ImageView otherpersonalChatIv;
    private ImageView otherpersonalSvipIv;
    private TextView otherpersonalNameTx;
    private TextView otherpersonalVipTx;
    private TextView otherpersonalDynamicNumberTx;
    private TextView otherpersonalVisitorNumTx;
    private TextView otherpersonalOftenaddrTv;
    private TextView otherpersonalRelationshipTv;
    private TextView otherpersonalRelationshipBt;
    private CustomImageView otherpersonalDynmicOneIv, otherpersonalDynmicTwoIv
            , otherpersonalDynmicThreeIv, otherpersonalDynmicFourIv;
    private RoundImageView otherpersonalVisitorOneIv, otherpersonalVisitorTwoIv, otherpersonalVisitorThreeIv;
    private RoundImageView otherpersonalGuardianOneIv, otherpersonalGuardianTwoIv, otherpersonalGuardianThreeIv;
    private ImageView otherpersonalGuardianTopOneIv, otherpersonalGuardianTopTwoIv, otherpersonalGuardianTopThreeIv;
    private RelativeLayout otherpersonalGuardianOneRl,otherpersonalGuardianTwoRl,otherpersonalGuardianThreeRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_otherpersonal);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent (Context context, long id) {
        Intent intent = new Intent(context, OtherPersonalActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        hideTitleBar();
        id = getIntent().getLongExtra("id", -1);
//        id = 450000168;
        LogUtils.e("id:"+id);

        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(getmWidth(), (int) (9.0F * (getmWidth() / 17.0F)));
        otherpersonalScrollview.setHeaderLayoutParams(localObject);

        otherpersonalBackLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_back_ll);
        otherpersonalBackLl.setOnClickListener(this);
        otherpersonalMoreLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_more_ll);
        otherpersonalMoreLl.setOnClickListener(this);
        otherpersonalDynamicLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynamic_ll);
        otherpersonalDynamicLl.setOnClickListener(this);
        otherpersonalVisitorLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_ll);
        otherpersonalVisitorLl.setOnClickListener(this);
        otherpersonalFriendsLl = (LinearLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_friends_ll);
        otherpersonalFriendsLl.setOnClickListener(this);

        otherpersonalChatIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_chat_iv);
        otherpersonalChatIv.setOnClickListener(this);
        otherpersonalHeadIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_head_iv);
        otherpersonalGenderIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_gender_iv);
        otherpersonalHeadVerifyImg = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_head_verify_img);
        otherpersonalSvipIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_svip_iv);
        otherpersonalNameTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_name_tx);
        otherpersonalVipTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_vip_tx);
        otherpersonalDynamicNumberTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynamic_number_tx);
        otherpersonalVisitorNumTx = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_num_tx);
        otherpersonalOftenaddrTv = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_oftenaddr_tv);
        otherpersonalRelationshipTv = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_relationship_tv);
        otherpersonalRelationshipBt = (TextView) otherpersonalScrollview.findViewById(R.id.otherpersonal_relationship_bt);
        otherpersonalRelationshipBt.setOnClickListener(this);

        otherpersonalDynmicOneIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_one_iv);
        otherpersonalDynmicTwoIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_two_iv);
        otherpersonalDynmicThreeIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_three_iv);
        otherpersonalDynmicFourIv = (CustomImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_dynmic_four_iv);

        otherpersonalVisitorOneIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_one_iv);
        otherpersonalVisitorTwoIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_two_iv);
        otherpersonalVisitorThreeIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_visitor_three_iv);

        otherpersonalGuardianOneIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_one_iv);
        otherpersonalGuardianTwoIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_two_iv);
        otherpersonalGuardianThreeIv = (RoundImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_three_iv);
        otherpersonalGuardianTopOneIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_one_iv);
        otherpersonalGuardianTopTwoIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_two_iv);
        otherpersonalGuardianTopThreeIv = (ImageView) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_top_three_iv);
        otherpersonalGuardianOneRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_one_rl);
        otherpersonalGuardianTwoRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_two_rl);
        otherpersonalGuardianThreeRl = (RelativeLayout) otherpersonalScrollview.findViewById(R.id.otherpersonal_guardian_three_rl);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_UserIndex + "/" + id)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERINDEX).id(Common.NET_GET_USERINDEX).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERINDEX) {
            mGetUserIndexModel = mGson.fromJson(data, GetUserIndexModel.class);
            updataView();
        } else if (id == Common.NET_ADD_BLACKLIST) {
            showToast("已成功拉黑");
        } else if (id == Common.NET_ADD_FRIEND) {
            showToast("添加好友请求已发出");
        } else if (id == Common.NET_DLELTE_FRIEND) {
            mGetUserIndexModel.getBody().setFriend(false);
            otherpersonalRelationshipBt.setText("加好友");
            otherpersonalRelationshipTv.setText("陌生人");
        }
    }

    private void updataView() {
        otherpersonalNameTx.setText(mGetUserIndexModel.getBody().getUserDetailBean().getNickName());
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()>=4) {
            otherpersonalVipTx.setVisibility(View.GONE);
            otherpersonalSvipIv.setVisibility(View.VISIBLE);
        } else {
            otherpersonalSvipIv.setVisibility(View.GONE);
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()==0) {
                otherpersonalVipTx.setVisibility(View.GONE);
            } else {
                otherpersonalVipTx.setText("VIP."+mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level());
                otherpersonalVipTx.setVisibility(View.VISIBLE);
            }
        }
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("M")) {
            otherpersonalGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            otherpersonalGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex().equals("F")) {
            if (mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()==1) {//已认证
                otherpersonalGenderIv.setVisibility(View.GONE);
                otherpersonalHeadVerifyImg.setVisibility(View.VISIBLE);
            } else {//没认证
                otherpersonalGenderIv.setVisibility(View.VISIBLE);
                otherpersonalHeadVerifyImg.setVisibility(View.GONE);
            }
        } else {
            otherpersonalHeadVerifyImg.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                ,otherpersonalHeadIv,MyBaseApplication.getApplication().getOptionsNot());

        otherpersonalDynamicNumberTx.setText(""+mGetUserIndexModel.getBody().getBlogImageMap().getDataSize());
        initDynamicIV();

        otherpersonalVisitorNumTx.setText(mGetUserIndexModel.getBody().getVisitorMap().getVisitorSize()+"");
        initVisitorIV();

        initguardianIV();

        if (mGetUserIndexModel.getBody().isFriend()) {
            otherpersonalRelationshipBt.setText("删除");
            otherpersonalRelationshipTv.setText("好友");
        } else {
            otherpersonalRelationshipBt.setText("加好友");
            otherpersonalRelationshipTv.setText("陌生人");
        }

        otherpersonalOftenaddrTv.setText(mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getAround_site());
    }

    private void initguardianIV() {
        int num = mGetUserIndexModel.getBody().getGuardlist().size();
        if (num==1) {
            otherpersonalGuardianOneRl.setVisibility(View.GONE);
            otherpersonalGuardianTwoRl.setVisibility(View.GONE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalGuardianOneRl.setVisibility(View.GONE);
            otherpersonalGuardianTwoRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            otherpersonalGuardianOneRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTwoRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianThreeRl.setVisibility(View.VISIBLE);
            otherpersonalGuardianTopOneIv.setImageResource(R.mipmap.other_guard_one);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(0).getIcon()
                    ,otherpersonalGuardianOneIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopTwoIv.setImageResource(R.mipmap.other_guard_two);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(1).getIcon()
                    ,otherpersonalGuardianTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            otherpersonalGuardianTopThreeIv.setImageResource(R.mipmap.other_guard_three);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getGuardlist().get(2).getIcon()
                    ,otherpersonalGuardianThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    private void initVisitorIV() {
        int num = mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().size();
        if (num==1) {
            otherpersonalVisitorOneIv.setVisibility(View.GONE);
            otherpersonalVisitorTwoIv.setVisibility(View.GONE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(0).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalVisitorOneIv.setVisibility(View.GONE);
            otherpersonalVisitorTwoIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(0).getIcon()
                    ,otherpersonalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(1).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=3) {
            otherpersonalVisitorOneIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorTwoIv.setVisibility(View.VISIBLE);
            otherpersonalVisitorThreeIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(0).getIcon()
                    ,otherpersonalVisitorOneIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(1).getIcon()
                    ,otherpersonalVisitorTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getVisitorMap().getVisitorList().get(2).getIcon()
                    ,otherpersonalVisitorThreeIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    private void initDynamicIV() {
        int num = mGetUserIndexModel.getBody().getBlogImageMap().getDataList().size();
        if (num==1) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.GONE);
            otherpersonalDynmicThreeIv.setVisibility(View.GONE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==2) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.GONE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num==3) {
            otherpersonalDynmicOneIv.setVisibility(View.GONE);
            otherpersonalDynmicTwoIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(2)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        } else if (num>=4) {
            otherpersonalDynmicOneIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicTwoIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicThreeIv.setVisibility(View.VISIBLE);
            otherpersonalDynmicFourIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(0)
                    ,otherpersonalDynmicOneIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(1)
                    ,otherpersonalDynmicTwoIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(2)
                    ,otherpersonalDynmicThreeIv,MyBaseApplication.getApplication().getOptionsNot());
            ImageLoader.getInstance().displayImage(Common.ImageUrl+mGetUserIndexModel.getBody().getBlogImageMap().getDataList().get(3)
                    ,otherpersonalDynmicFourIv,MyBaseApplication.getApplication().getOptionsNot());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERINDEX);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BLACKLIST);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_FRIEND);
        OkHttpUtils.getInstance().cancelTag(Common.NET_DLELTE_FRIEND);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otherpersonal_relationship_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (mGetUserIndexModel.getBody().isFriend()) {
                    PromptDialog.Builder builder = new PromptDialog.Builder(this);
                    builder.setMessage("您确定要删除好友吗？");
                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            OkHttpUtils.delete().url(Common.Url_Delete_Friends+id)
                                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                    .tag(Common.NET_DLELTE_FRIEND).id(Common.NET_DLELTE_FRIEND).build()
                                    .execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
                        }
                    });
                    builder.show();
                } else {
                    OkHttpUtils.postString().url(Common.Url_Add_Friends+id).content("").mediaType(Common.JSON)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_ADD_FRIEND).id(Common.NET_ADD_FRIEND).build()
                            .execute(new MyStringCallback(mContext, this, true));
                }
                break;
            case R.id.otherpersonal_back_ll:
                finish();
                break;
            case R.id.otherpersonal_visitor_ll:
                if (!isLogin() || mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()<1) {
                    showWraning("VIP.1以上用户才有权限查看");
                    return;
                }
                startActivity(HisVisitorActivity.buildIntent(mContext, id, "TA的访客"));
                break;
            case R.id.otherpersonal_friends_ll:
                if (!isLogin() || mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVip_level()<2) {
                    showWraning("VIP.2以上用户才有权限查看");
                    return;
                }
                startActivity(HisGuardianActivity.buildIntent(mContext, id, "TA的守护者"));
                break;
            case R.id.otherpersonal_dynamic_ll:
                startActivity(HisDynamicsActivity.buildIntent(mContext, id));
                break;
            case R.id.otherpersonal_more_ll:
                showBottomImg();
                break;
            case R.id.otherpersonal_chat_iv:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                startActivity(P2PChatActivity.buildIntent(mContext, String.valueOf(id)
                        , mGetUserIndexModel.getBody().getUserDetailBean().getNickName()
                        , mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getSex()
                        , mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                        , mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getVerify_status()));
                break;
        }
    }

    private void showBottomImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        String[] str = new String[]{"举报", "把TA加入黑名单"};
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                if (which == 0) {//举报
                    startActivity(ReportActivity.buildIntent(mContext, id));
                } else if (which == 1) {//把TA加入黑名单
                    if (MyBaseApplication.mGetUserIndexModel.getBody().getUserDetailBean().getUserExt().getUser_id() == id) {
                        showToast("不能将自己加入黑名单");
                        return;
                    }
                    OkHttpUtils.postString().url(Common.Url_Add_BlackList)
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .content("{\"black_user_id\":" + id + "}")
                            .mediaType(Common.JSON).id(Common.NET_ADD_BLACKLIST).tag(Common.NET_ADD_BLACKLIST)
                            .build().execute(new MyStringCallback(mContext, OtherPersonalActivity.this, true));
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
}
