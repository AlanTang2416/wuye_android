package com.atman.wysq.ui.community;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.atman.wysq.R;
import com.atman.wysq.adapter.PostContentListAdapter;
import com.atman.wysq.model.request.AddPostModel;
import com.atman.wysq.model.request.PostContentModel;
import com.atman.wysq.model.response.HeadImgResultModel;
import com.atman.wysq.model.response.HeadImgSuccessModel;
import com.atman.wysq.ui.MainActivity;
import com.atman.wysq.ui.base.BaseGestureLockActivity;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.login.LoginActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.Tools;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.face.FaceNotEditViewRelativeLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.BottomDialog;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.PromptDialog;
import com.choicepicture_library.ImageGridActivity;
import com.choicepicture_library.model.SelectImageItem;
import com.choicepicture_library.tools.Bimp;
import com.tbl.okhttputils.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/3 14:05
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PostActivity extends MyBaseActivity implements AdapterInterface,View.OnTouchListener {

    @Bind(R.id.post_photograph_iv)
    ImageView postPhotographIv;
    @Bind(R.id.post_album_iv)
    ImageView postAlbumIv;
    @Bind(R.id.post_face_iv)
    ImageView postFaceIv;
    @Bind(R.id.post_ll1)
    LinearLayout postLl1;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.post_FaceRelativeLayout)
    FaceNotEditViewRelativeLayout postFaceRelativeLayout;
    @Bind(R.id.post_content_lv)
    ListView postContentLv;

    private Context mContext = PostActivity.this;
    private RelativeLayout barRightRl;
    private List<PostContentModel> mPostContentModelList = new ArrayList<>();
    private EditText mFocusEditText;
    private int blogBoardId;

    private View headView;
    private MyCleanEditText postTitleEt;
    private PostContentListAdapter mAdapter;
    private int mPosition;
    private int mAnonymity = 0;

    private String path = "";
    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private boolean isBottom = true;
    private int MaxLen = 10;
    private List<AddPostModel> mAddPostModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, int blogBoardId){
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("blogBoardId", blogBoardId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        blogBoardId = getIntent().getIntExtra("blogBoardId", -1);
        setBarTitleTx("发布秘密");
        setBarRightIv(R.mipmap.bt_create_ok);
        barRightRl = getBarRightRl();
        barRightRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postTitleEt.getText().toString().trim().isEmpty()) {
                    showToast("请输入标题");
                    return;
                }
                boolean num = false;
                for(int i=0;i<mAdapter.getCount();i++){
                    if (mAdapter.getCount()==1 && mAdapter.getItem(i).getLocalUrl().isEmpty()
                            && mAdapter.getItem(i).getContent().trim().isEmpty()) {
                        num = true;
                    }
                }
                if (num) {
                    showToast("帖子内容不能为空");
                    return;
                }
                showWarn();
            }
        });
        getBarBackLl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });
        getBarBackIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonot();
            }
        });

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_post_head_view, null);
        postTitleEt = (MyCleanEditText) headView.findViewById(R.id.post_title_et);
        postTitleEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditFocus(postTitleEt, 0);
            }
        });
        postTitleEt.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setEditFocus(postTitleEt, 0);
                }
            }
        });

        initData();

        postPhotographIv.setOnTouchListener(this);
        postAlbumIv.setOnTouchListener(this);
        postFaceIv.setOnTouchListener(this);
        barRightRl.setOnTouchListener(this);
    }

    private void initData() {
        PostContentModel mPostContentModel = new PostContentModel(1, "", "", "", null);
        mPostContentModelList.add(mPostContentModel);
        mAdapter = new PostContentListAdapter(mContext, mPostContentModelList, postFaceIv, llFacechoose, postFaceRelativeLayout, this);
        postContentLv.addHeaderView(headView);
        postContentLv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if(id == Common.NET_RESET_HEAD){
            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            if (mHeadImgResultModel!=null && mHeadImgResultModel.getFiles().size()>0 ) {
                if (!mHeadImgResultModel.getFiles().get(0).isSuccessful()) {
                    showToast("上传失败");
                    MyBaseApplication.getApplication().setFilterLock(false);
                } else {
                    HeadImgSuccessModel mHeadImgSuccessModel = mGson.fromJson(data, HeadImgSuccessModel.class);
                    LogUtils.e("inUrl:"+inUrl+",setNetUrl:"+mHeadImgSuccessModel.getFiles().get(0).getUrl());
                    mAdapter.getItem(inUrl).setNetUrl(mHeadImgSuccessModel.getFiles().get(0).getUrl());
                    inUrl += 1;
                    upLoadPic();
                }
            }
        } else if (id==Common.NET_ADD_POST) {
            showToast("发帖成功");
            finish();
        }
    }

    private int inUrl = 0;
    private void upLoadPic () {
        mAddPostModelList.clear();
        if (inUrl >= mAdapter.getCount()) {
            String str = "";
            for (int i=0;i<mPostContentModelList.size();i++) {
                if (!mPostContentModelList.get(i).getNetUrl().isEmpty()) {
                    str += "<wysqimg="+mPostContentModelList.get(i).getNetUrl()+"=wysqimg>"+mPostContentModelList.get(i).getContent();
                } else {
                    str += mPostContentModelList.get(i).getContent();
                }
            }
            AddPostModel addPostModel = new AddPostModel(blogBoardId, str, postTitleEt.getText().toString().trim(), mAnonymity);
            mAddPostModelList.add(addPostModel);
            OkHttpUtils.postString().url(Common.Url_Add_Post).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).content(mGson.toJson(mAddPostModelList))
                    .id(Common.NET_ADD_POST).tag(Common.NET_ADD_POST).build().execute(new MyStringCallback(mContext, this, true));
            return;
        }
        String data = mAdapter.getItem(inUrl).getLocalUrl();
        if (data.isEmpty()) {
            inUrl += 1;
            upLoadPic();
            return;
        } else {
            Uri imageUri = Uri.parse("file:///" + data);
            try {
                String path = Tools.revitionImage(imageUri.getPath());
                OkHttpUtils.post().url(Common.Url_Reset_Head)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .addParams("uploadType", "img")
                        .addFile("files0_name", StringUtils.getFileName(path),
                                new File(path)).id(Common.NET_RESET_HEAD)
                        .tag(Common.NET_RESET_HEAD).build().readTimeOut(Common.timeOutTwo).writeTimeOut(Common.timeOutTwo)
                        .execute(new MyStringCallback(mContext, this, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();
    }

    private void clear() {
        Bimp.num = 0;
        Bimp.drr.clear();
        Bimp.drr_or.clear();
        Bimp.bmp.clear();
    }

    @OnClick({R.id.post_photograph_iv, R.id.post_album_iv, R.id.post_face_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_face_iv:
                if (mFocusEditText != null) {
                    postFaceRelativeLayout.setEt_sendmessage(mFocusEditText);
                }
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                // 隐藏表情选择框
                if (llFacechoose.getVisibility() == View.VISIBLE) {
                    llFacechoose.setVisibility(View.GONE);
                } else {
                    llFacechoose.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.post_photograph_iv:
                if (mAdapter.getCount()>=MaxLen) {
                    showToast("最多创建10条内容");
                    return;
                }
                MyBaseApplication.getApplication().setFilterLock(true);
                isBottom = true;
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                break;
            case R.id.post_album_iv:
                if (mAdapter.getCount()>=MaxLen) {
                    showToast("最多创建10条内容");
                    return;
                }
                isBottom = true;
                Bimp.max = MaxLen - mAdapter.getCount();
                clear();
                startActivityForResult(new Intent(mContext, ImageGridActivity.class), CHOOSE_BIG_PICTURE);
                break;
        }
    }

    private void setEditFocus(EditText postTitleEt, int i) {
        mFocusEditText = postTitleEt;
        postFaceRelativeLayout.setEt_sendmessage(postTitleEt);
        if (llFacechoose.getVisibility() == View.VISIBLE) {
            llFacechoose.setVisibility(View.GONE);
        }
        if (i == 0) {
            postFaceIv.setVisibility(View.GONE);
        } else {
            postFaceIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_postcontent_delete_iv:
                mAdapter.delateItem(position);
                break;
            case R.id.item_postcontent_head_rl:
                showHeadImg();
                break;
        }
    }

    private void showHeadImg() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setTitle(Html.fromHtml("<font color=\"#f9464a\">头像修改</font>"));
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyBaseApplication.getApplication().setFilterLock(true);
                isBottom = false;
                if (which == 0) {//拍照
                    path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                } else {//选择照片
//                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//                    getAlbum.setType("image/*");
//                    startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                    Bimp.max = 1;
                    clear();
                    startActivityForResult(new Intent(mContext, ImageGridActivity.class), CHOOSE_BIG_PICTURE);
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

    private void showWarn() {
        BottomDialog.Builder builder = new BottomDialog.Builder(mContext);
        builder.setItems(new String[]{"发布", "匿名发布"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//发布
                    mAnonymity = 0;
                } else {//匿名发布
                    mAnonymity = 1;
                }
                boolean isGif = false;
                int num = 0;
                for (int i=0;i<mAdapter.getCount();i++) {
                    String str = mAdapter.getItem(i).getLocalUrl();
                    if ((str.substring(str.lastIndexOf(".")+1)).contains("gif")) {
                        isGif = true;
                        num = i;
                        if (mAdapter.getItem(0).getLocalUrl().isEmpty()) {
                            num -= 1;
                        }
                        break;
                    }
                }
                if (!isGif) {
                    inUrl = 0;
                    upLoadPic();
                } else {
                    showToast("第"+(num+1)+"张是gif图片，不可选用");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyBaseApplication.getApplication().setFilterLock(false);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
            if (isBottom) {
                for (int i=0;i<Bimp.drr.size();i++) {
                    PostContentModel mPostContentModel = new PostContentModel(1, Bimp.drr.get(i), "", "", null);
                    mPostContentModelList.add(mPostContentModel);
                }
            } else {
                for (int i=0;i<Bimp.drr.size();i++) {
                    mAdapter.setLocalUrl(mPosition, Bimp.drr.get(i));
                }
            }
        } else if (requestCode == TAKE_BIG_PICTURE) {
            if (!path.isEmpty()) {
                if (isBottom) {
                    PostContentModel mPostContentModel = new PostContentModel(1, path, "", "", null);
                    mPostContentModelList.add(mPostContentModel);
                } else {
                    mAdapter.setLocalUrl(mPosition, path);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键
            showDonot();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDonot() {
        PromptDialog.Builder builder = new PromptDialog.Builder(this);
        builder.setMessage("确定取消发布？");
        builder.setPositiveButton("不发了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("继续编辑", new DialogInterface.OnClickListener() {
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
}
