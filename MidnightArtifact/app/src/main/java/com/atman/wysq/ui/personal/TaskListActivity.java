package com.atman.wysq.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MyTaskAllAdapter;
import com.atman.wysq.model.response.GetTaskAllModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.ShareHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/12 11:44
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class TaskListActivity extends MyBaseActivity implements AdapterInterface, UMShareListener {

    @Bind(R.id.mytask_listview)
    ListView mytaskListview;

    private Context mContext = TaskListActivity.this;
    private MyTaskAllAdapter mAdapter;
    private int mPosition;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("任务");
        initListView();
    }

    private void initListView() {
        mAdapter = new MyTaskAllAdapter(mContext);
        mytaskListview.setAdapter(mAdapter);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Task)
                .addHeader("cookie", MyBaseApplication.getApp().getCookie())
                .tag(Common.NET_GET_RASK).id(Common.NET_GET_RASK).build()
                .execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyBaseApplication.getApp().setFilterLock(false);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_RASK) {
            GetTaskAllModel mGetTaskAllModel = mGson.fromJson(data, GetTaskAllModel.class);
            List<GetTaskAllModel.BodyEntity> body = new ArrayList<>();
            for(int i=0;i<mGetTaskAllModel.getBody().size();i++) {
                if (mGetTaskAllModel.getBody().get(i).getTask_type() != 8
                        && mGetTaskAllModel.getBody().get(i).getTask_type() != 9
                        && mGetTaskAllModel.getBody().get(i).getTask_type() != 10
                        && mGetTaskAllModel.getBody().get(i).getTask_type() != 11) {
                    body.add(mGetTaskAllModel.getBody().get(i));
                }
            }
            mAdapter.setBody(body, this);
        } else if (id == Common.NET_REWARDED) {
            mAdapter.changeRewardedStatusById(mPosition);
        } else if (id == Common.NET_FINISH_TASK) {
            mAdapter.changeFinishStatusById(mPosition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_RASK);
        OkHttpUtils.getInstance().cancelTag(Common.NET_REWARDED);
        OkHttpUtils.getInstance().cancelTag(Common.NET_FINISH_TASK);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_task_status_bt:
                GetTaskAllModel.BodyEntity mBodyEntity = mAdapter.getItem(position);
                id = mBodyEntity.getTask_id();
                mPosition = position;
                if (mBodyEntity.getTask_complete() == 0) {//未完成
                    if (mBodyEntity.getTask_type() == 1) {//签到
                    } else if (mBodyEntity.getTask_type() == 3) {//分享
                        SHARE_MEDIA Platform = null;
                        if (mBodyEntity.getShare_type() == 1) {//分享QQ
                            Platform = SHARE_MEDIA.QQ;
                        } else if (mBodyEntity.getShare_type() == 2) {//分享微信
                            Platform = SHARE_MEDIA.WEIXIN;
                        } else if (mBodyEntity.getShare_type() == 3) {//分享新浪微博
                            Platform = SHARE_MEDIA.SINA;
                        }
                        MyBaseApplication.getApp().setFilterLock(true);
                        ShareHelper.share(TaskListActivity.this, Platform, MyBaseApplication.mDownLoad_URL ,TaskListActivity.this);
                    } else if (mBodyEntity.getTask_type() == 4) {//安装
                    }
                } else {
                    if (mBodyEntity.getRewarded() == 0) {//未领取
                        OkHttpUtils.postString().url(Common.Url_Rewarded).content("{\"task_id\":"+id+"}")
                                .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                                .id(Common.NET_REWARDED).tag(Common.NET_REWARDED).build()
                                .execute(new MyStringCallback(mContext, TaskListActivity.this, true));
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult( requestCode, resultCode, data);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        showToast("分享成功");
        OkHttpUtils.postString().url(Common.Url_Finish_Task).content("{\"task_id\":"+id+"}")
                .mediaType(Common.JSON).addHeader("cookie",MyBaseApplication.getApp().getCookie())
                .id(Common.NET_FINISH_TASK).tag(Common.NET_FINISH_TASK).build()
                .execute(new MyStringCallback(mContext, TaskListActivity.this, true));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//        LogUtils.e("throwable.toString()"+throwable.toString());//auth faild
//        if (share_media == SHARE_MEDIA.SINA) {
//            showToast("分享失败,"+throwable.toString());
//        } else {
//        }
        showToast("分享失败, 请稍后再试");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        showToast("取消分享");
    }
}
