package com.atman.wysq.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.response.CheckVersionModel;
import com.atman.wysq.model.response.GetUserInfoModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.DataCleanManager;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.CustomImageView;
import com.base.baselibs.widget.PromptDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述 我的资料
 * 作者 tangbingliang
 * 时间 16/7/5 14:16
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyInformationActivity extends MyBaseActivity {

    @Bind(R.id.myinfo_username_tx)
    TextView myinfoUsernameTx;
    @Bind(R.id.myinfo_nick_tx)
    TextView myinfoNickTx;
    @Bind(R.id.myinfo_head_iv)
    CustomImageView myinfoHeadIv;
    @Bind(R.id.myinfo_gender_tx)
    TextView myinfoGenderTx;
    @Bind(R.id.myinfo_level_tx)
    TextView myinfoLevelTx;
    @Bind(R.id.myinfo_nextlevel_tx)
    TextView myinfoNextlevelTx;
    @Bind(R.id.myinfo_gesturelock_tx)
    TextView myinfoGesturelockTx;
    @Bind(R.id.myinfo_cache_tx)
    TextView myinfoCacheTx;
    @Bind(R.id.myinfo_version_tx)
    TextView myinfoVersionTx;
    @Bind(R.id.myinfo_version_ll)
    LinearLayout myinfoVersionLl;

    private Context mContext = MyInformationActivity.this;
    private GetUserInfoModel mGetUserInfoModel;
    private String mCacheSize;
    private String mCachePath = "/data/data/com.atman.wysq/";
    private LinearLayout backLl;
    private boolean isOpen = false;
    private String mHeadImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformation);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOpen = PreferenceUtil.getBoolPreferences(mContext, PreferenceUtil.PARM_ISOPEN_GESTURE);
        if (isOpen) {
            myinfoGesturelockTx.setText("开启");
        } else {
            myinfoGesturelockTx.setText("关闭");
        }
        if (isLogin()) {
            OkHttpUtils.get().url(Common.Url_GetUserInfo + MyBaseApplication.getApp().getmUserId())
                    .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                    .tag(Common.NET_GETUSERINFO).id(Common.NET_GETUSERINFO).build()
                    .execute(new MyStringCallback(mContext, this, true));
        }
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的账户");
        backLl = getBarBackLl();
        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        myinfoVersionLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showToast("渠道：" + MyBaseApplication.mChannel);
                return true;
            }
        });
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
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GETUSERINFO) {
            mGetUserInfoModel = mGson.fromJson(data, GetUserInfoModel.class);
            UpDateUI();
        } else if (id == Common.NET_RESET_HEAD) {
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("头像修改失败");
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    mHeadImgUrl = mHeadImgSuccessModel.getFiles().get(0).getUrl();
                    OkHttpUtils.postString().url(Common.Url_Modify_Head).id(Common.NET_MODIFY_HEAD)
                            .content("{\"icon\":\""+ mHeadImgUrl +"\"}")
                            .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApp().getCookie())
                            .tag(Common.NET_MODIFY_HEAD).build().execute(new MyStringCallback(mContext, this, true));
                }
            }
        } else if (id == Common.NET_MODIFY_HEAD) {
            showToast("头像修改成功");
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mHeadImgUrl
                    , myinfoHeadIv, MyBaseApplication.getApp().getOptions());
        } else if (id == Common.NET_LOGOUT) {
            NIMClient.getService(AuthService.class).logout();
            showToast("已退出登录");
            clearData();
        } else if (id == Common.NET_GET_VERSION) {
            final CheckVersionModel mCheckVersionModel = mGson.fromJson(data, CheckVersionModel.class);
            if (mCheckVersionModel.getResult().equals("1") && mCheckVersionModel.getBody()!=null) {
                PromptDialog.Builder builder = new PromptDialog.Builder(MyInformationActivity.this);
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
            } else {
                showToast("已是最新版本");
            }
        }
    }

    private void UpDateUI() {
        myinfoUsernameTx.setText(mGetUserInfoModel.getBody().getUserExt().getMobile());
        myinfoNickTx.setText(mGetUserInfoModel.getBody().getUserExt().getNick_name());
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mGetUserInfoModel.getBody().getUserExt().getIcon()
                , myinfoHeadIv, MyBaseApplication.getApp().getOptions());
        if (mGetUserInfoModel.getBody().getUserExt().getSex().equals("M")) {
            myinfoGenderTx.setText("男");
        } else {
            myinfoGenderTx.setText("女");
        }
        myinfoLevelTx.setText("Lv" + mGetUserInfoModel.getBody().getUserExt().getUserLevel());
        myinfoNextlevelTx.setText("距离升至下一等级\n还需" + mGetUserInfoModel.getBody().getUserExt().getNext_level_integral() + "经验");

        myinfoVersionTx.setText(MyBaseApplication.mVersionName);
        countCache();
    }

    private void countCache() {
        try {
            mCacheSize = DataCleanManager.getCacheSize(new File(mCachePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        myinfoCacheTx.setText(mCacheSize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GETUSERINFO);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_HEAD);
        OkHttpUtils.getInstance().cancelTag(Common.NET_LOGOUT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_MODIFY_HEAD);
    }

    @OnClick({R.id.myinfo_reset_pw_ll, R.id.myinfo_nick_ll, R.id.myinfo_head_iv, R.id.myinfo_head_ll
            , R.id.myinfo_privacy_ll, R.id.myinfo_gesturelock_ll, R.id.myinfo_cache_ll
            , R.id.myinfo_version_ll, R.id.myinfo_logout_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myinfo_reset_pw_ll:
                startActivityForResult(new Intent(mContext, ModifyPasswordActivity.class), Common.toResetPW);
                break;
            case R.id.myinfo_nick_ll:
                startActivityForResult(ModifyNickAcitvity.buildIntent(mContext
                        , mGetUserInfoModel.getBody().getUserExt().getName_change()), Common.toResetNICK);
                break;
            case R.id.myinfo_head_iv:
            case R.id.myinfo_head_ll:
                showHeadImg(view);
                break;
            case R.id.myinfo_privacy_ll:
                break;
            case R.id.myinfo_gesturelock_ll:
                startActivityForResult(new Intent(mContext, GestureLockSettingActivity.class), Common.toSettingGesrure);
                break;
            case R.id.myinfo_cache_ll:
                showCleanData();
                break;
            case R.id.myinfo_version_ll:
                showToast("检查更新版本...");
                OkHttpUtils.get().url(Common.Url_Get_Version+"?version="+MyBaseApplication.mVersionName.replace("v",""))
                        .addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .id(Common.NET_GET_VERSION).tag(Common.NET_GET_VERSION).build().execute(new MyStringCallback(mContext, this, false));
                break;
            case R.id.myinfo_logout_bt:
                showExit();
                break;
        }
    }

    private void showExit() {
        PromptDialog.Builder builder = new PromptDialog.Builder(MyInformationActivity.this);
        builder.setMessage("你确定要退出登录吗？");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.postString().url(Common.Url_Logout).tag(Common.NET_LOGOUT).id(Common.NET_LOGOUT)
                        .content("{logout}").mediaType(Common.JSON)
                        .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                        .build().execute(new MyStringCallback(mContext, MyInformationActivity.this, true));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showCleanData() {
        PromptDialog.Builder builder = new PromptDialog.Builder(MyInformationActivity.this);
        builder.setMessage("你确定清除应用缓存吗？");
        builder.setPositiveButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataCleanManager.cleanApplicationData(mContext, mCachePath);
                countCache();
                myinfoCacheTx.setText("0 Byte");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private Uri imageUri;//The Uri to store the big bitmap
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private final int CROP_BIG_PICTURE = 666;
    private int outputX = 350;
    private String path = "";

    private void showHeadImg(View view) {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">头像修改</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyBaseApplication.getApp().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.toResetPW || requestCode == Common.toSettingGesrure) {
            back();
        } else if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            imageUri = data.getData();
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == TAKE_BIG_PICTURE) {
            imageUri = Uri.parse("file:///" + path);
            cropImageUri(imageUri, outputX, outputX, CROP_BIG_PICTURE);
        } else if (requestCode == CROP_BIG_PICTURE) {
            if (imageUri != null) {
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApp().getCookie())
                        .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()),
                        new File(imageUri.getPath())).id(Common.NET_RESET_HEAD)
                        .tag(Common.NET_RESET_HEAD).build().execute(new MyStringCallback(mContext, this, true));
            }
        }
    }

    //裁减照片
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        if (uri == null) {
            return;
        }
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

    private void back() {
        Intent mIntent = new Intent();
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return super.onKeyDown(keyCode, event);
    }
}
