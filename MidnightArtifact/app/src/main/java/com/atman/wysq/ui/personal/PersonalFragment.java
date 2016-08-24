package com.atman.wysq.ui.personal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.GetTaskAllModel;
import com.atman.wysq.model.response.GetUserInfoModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.ui.mall.order.MyOrderListActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.pullzoom.PullZoomScrollVIew;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 我的
 * 作者 tangbingliang
 * 时间 16/7/1 18:13
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PersonalFragment extends MyBaseFragment implements View.OnClickListener {

    @Bind(R.id.personal_scrollview)
    PullZoomScrollVIew personalScrollview;

    private ImageView personalSettingIv;
    private ImageView personalGenderIv;
    private ImageView personalTaskIv;
    private CustomImageView personalHeadIv;
    private CustomImageView personalHeadVerifyImg;
    private TextView personalNameTx;
    private TextView personalGendercertificationTv;
    private GetUserInfoModel mGetUserInfoModel;

    private LinearLayout personalMaillistLl;
    private LinearLayout personalServiceLl;
    private LinearLayout personalTaskLl;
    private LinearLayout personalRechargeLl;
    private LinearLayout personalMyorderLl;

    private String mHeadImgUrl;
    private boolean isHead = false;

    private int PICK_FROM_CAMERA = 777;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);
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

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 19.0F)));
        personalScrollview.setHeaderLayoutParams(localObject);

        personalSettingIv = (ImageView) personalScrollview.findViewById(R.id.personal_setting_iv);
        personalGenderIv = (ImageView) personalScrollview.findViewById(R.id.personal_gender_iv);
        personalTaskIv = (ImageView) personalScrollview.findViewById(R.id.personal_task_iv);
        personalNameTx = (TextView) personalScrollview.findViewById(R.id.personal_name_tx);
        personalGendercertificationTv = (TextView) personalScrollview.findViewById(R.id.personal_gendercertification_tv);
        personalHeadIv = (CustomImageView) personalScrollview.findViewById(R.id.personal_head_iv);
        personalHeadVerifyImg = (CustomImageView) personalScrollview.findViewById(R.id.personal_head_verify_img);
        personalMaillistLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_maillist_ll);
        personalServiceLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_service_ll);
        personalTaskLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_task_ll);
        personalRechargeLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_recharge_ll);
        personalMyorderLl = (LinearLayout) personalScrollview.findViewById(R.id.personal_myorder_ll);

        personalSettingIv.setOnClickListener(this);
        personalHeadIv.setOnClickListener(this);
        personalNameTx.setOnClickListener(this);
        personalMaillistLl.setOnClickListener(this);
        personalServiceLl.setOnClickListener(this);
        personalTaskLl.setOnClickListener(this);
        personalRechargeLl.setOnClickListener(this);
        personalGendercertificationTv.setOnClickListener(this);
        personalMyorderLl.setOnClickListener(this);

    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();

    }

    public void doHttp() {
        if (!isLogin()) {
            hitSetring();
        } else {
            OkHttpUtils.get().url(Common.Url_GetUserInfo+ PreferenceUtil.getPreferences(getActivity(), PreferenceUtil.PARM_USERID))
                    .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                    .tag(Common.NET_GETUSERINFO).id(Common.NET_GETUSERINFO).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
        }
    }

    private void hitSetring() {
        personalHeadIv.setImageResource(R.mipmap.ic_launcher);
        personalSettingIv.setVisibility(View.INVISIBLE);
        personalGenderIv.setVisibility(View.INVISIBLE);
        personalGendercertificationTv.setVisibility(View.INVISIBLE);
        personalTaskIv.setVisibility(View.INVISIBLE);
        personalHeadVerifyImg.setVisibility(View.INVISIBLE);
        personalNameTx.setText("请点击登录");
    }

    @Override
    public void onResume() {
        super.onResume();
        doHttp();
        LogUtils.e("onResume()");
        if (!isHead) {
            MyBaseApplication.getApp().setFilterLock(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GETUSERINFO) {
            mGetUserInfoModel = mGson.fromJson(data, GetUserInfoModel.class);
            MyBaseApplication.mGetUserInfoModel = mGetUserInfoModel;
            UpDateUI();
            OkHttpUtils.get().url(Common.Url_Get_Task)
                    .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                    .tag(Common.NET_GET_RASK).id(Common.NET_GET_RASK).build()
                    .execute(new MyStringCallback(getActivity(), this, true));
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("头像修改失败");
                    isHead = false;
                    MyBaseApplication.getApp().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    OkHttpUtils.postString().url(Common.Url_Modify_Head).id(Common.NET_MODIFY_HEAD)
                            .content("{\"icon\":\""+ mHeadImgUrl +"\"}")
                            .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                            .tag(Common.NET_MODIFY_HEAD).build().execute(new MyStringCallback(getActivity(), this, true));
                }
            }
        } else if (id == Common.NET_RESET_HEAD_TWO) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("照片上传失败");
                    isHead = false;
                    MyBaseApplication.getApp().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    OkHttpUtils.postString().url(Common.Url_Verify).id(Common.NET_VERIFY)
                            .content("{\"verify_pic\":\""+ mHeadImgUrl +"\"}")
                            .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                            .tag(Common.NET_VERIFY).build().execute(new MyStringCallback(getActivity(), this, true));
                }
            }
        } else if (id == Common.NET_MODIFY_HEAD) {
            isHead = false;
            MyBaseApplication.getApp().setFilterLock(false);
            showToast("头像修改成功");
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mHeadImgUrl
                    , personalHeadIv, MyBaseApplication.getApp().getOptions());
        } else if (id == Common.NET_VERIFY) {
            showToast("提交成功，请等待审核！");
        } else if (id == Common.NET_GET_RASK) {
            GetTaskAllModel mGetTaskAllModel = mGson.fromJson(data, GetTaskAllModel.class);
            for (int i=0;i<mGetTaskAllModel.getBody().size();i++) {
                if (mGetTaskAllModel.getBody().get(i).getTask_complete() == 1
                        && mGetTaskAllModel.getBody().get(i).getRewarded() == 0) {
                    personalTaskIv.setVisibility(View.VISIBLE);
                    break;
                } else {
                    personalTaskIv.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        isHead = false;
        MyBaseApplication.getApp().setFilterLock(false);
    }

    private void UpDateUI() {
        MyBaseApplication.mUserCion = mGetUserInfoModel.getBody().getUserExt().getGold_coin();
        MyBaseApplication.mHEAD_URL = mGetUserInfoModel.getBody().getUserExt().getIcon();
        personalSettingIv.setVisibility(View.VISIBLE);
        personalGenderIv.setVisibility(View.VISIBLE);
        personalNameTx.setText(mGetUserInfoModel.getBody().getNickName());
        if (mGetUserInfoModel.getBody().getUserExt().getSex().equals("M")) {
            personalGenderIv.setImageResource(R.mipmap.personal_man_ic);
        } else {
            personalGenderIv.setImageResource(R.mipmap.personal_weman_ic);
        }
        if (mGetUserInfoModel.getBody().getUserExt().getSex().equals("F")) {
            if (mGetUserInfoModel.getBody().getUserExt().getVerify_status()==1) {//已认证
                personalGendercertificationTv.setVisibility(View.GONE);
                personalGenderIv.setVisibility(View.GONE);
                personalHeadVerifyImg.setVisibility(View.VISIBLE);
            } else {//没认证
                personalGendercertificationTv.setVisibility(View.VISIBLE);
                personalGenderIv.setVisibility(View.VISIBLE);
                personalHeadVerifyImg.setVisibility(View.GONE);
            }
        } else {
            personalGendercertificationTv.setVisibility(View.GONE);
            personalHeadVerifyImg.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mGetUserInfoModel.getBody().getUserExt().getIcon()
                , personalHeadIv, MyBaseApplication.getApp().getOptions());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETUSERINFO);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_myorder_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                }
                break;
            case R.id.personal_gendercertification_tv:
                MyBaseApplication.getApp().setFilterLock(true);
                path = UiHelper.photoBefor(getActivity(), path, PICK_FROM_CAMERA);
                break;
            case R.id.personal_setting_iv:
                getActivity().startActivityForResult(new Intent(getActivity(), MyInformationActivity.class), Common.toMyInfo);
                break;
            case R.id.personal_head_iv:
                if (!isLogin()) {
                    toLogin();
                } else {
                    showHeadImg(v);
                }
                break;
            case R.id.personal_name_tx:
                if (!isLogin()) {
                    toLogin();
                }
                break;
            case R.id.personal_maillist_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (!UiHelper.isTabletDevice(getActivity())) {
                        getActivity().startActivity(new Intent(getActivity(), AddressListInvitationActivity.class));
                    } else {
                        showToast("你的设备不支持短信");
                    }
                }
                break;
            case R.id.personal_service_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    showWran(getResources().getString(R.string.personal_service_phone_str));
                }
                break;
            case R.id.personal_task_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivity(new Intent(getActivity(), TaskListActivity.class));
                }
                break;
            case R.id.personal_recharge_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    getActivity().startActivity(new Intent(getActivity(), RechargeActivity.class));
                }
                break;
        }
    }

    public void showWran(final String str) {
        PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
        builder.setMessage("客服电话："+str);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("呼叫", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MyBaseApplication.getApp().setFilterLock(true);
                toPhone(getActivity(), str);
            }
        });
        builder.show();
    }

    private void toLogin() {
        getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class), Common.toLogin);
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private final int CROP_BIG_PICTURE = 666;
    private int outputX = 350;
    private String path = "";
    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">头像修改</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(getActivity(), path, TAKE_BIG_PICTURE);
                } else {//选择照片
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType("image/*");
                    startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                }
                MyBaseApplication.getApp().setFilterLock(true);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        MyBaseApplication.getApp().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toMyInfo) {
            if (!isLogin()) {
                hitSetring();
                toLogin();
            }
        } else if (requestCode == Common.toLogin) {
//            LogUtils.e("Common.toLogin:"+Common.toLogin);
            doHttp();
        } else if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == CROP_BIG_PICTURE) {
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()),
                                new File(imageUri.getPath())).id(Common.NET_RESET_HEAD)
                        .tag(Common.NET_RESET_HEAD).build().execute(new MyStringCallback(getActivity(), this, true));
            }
        } else if (requestCode == PICK_FROM_CAMERA) {
            imageUri = Uri.parse("file:///" + path);
            LogUtils.e("imageUri:"+imageUri);
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()),
                                new File(imageUri.getPath())).id(Common.NET_RESET_HEAD_TWO)
                        .tag(Common.NET_RESET_HEAD_TWO).build().execute(new MyStringCallback(getActivity(), this, true));
            }
        }
    }

    //裁减照片
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        if (uri == null) {
            return;
        }
        isHead = true;
        MyBaseApplication.getApp().setFilterLock(true);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        imageUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
