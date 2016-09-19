package com.atman.wysq.ui.yunxinfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.MessageCenterAdapter;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.TouChuanOtherNoticeDao;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by vavid on 2016/9/19.
 */
public class MessageCenterActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.messagecenter_listview)
    ListView messagecenterListview;

    private Context mContext = MessageCenterActivity.this;

    private MessageCenterAdapter mAdapter;
    private TouChuanOtherNoticeDao mTouChuanOtherNoticeDao;
    private List<TouChuanOtherNotice> mTouChuanOtherNotice;
    private String name;
    private long ueseID;

    private View mEmpty;
    private TextView mEmptyTX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_messagecenter);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, MessageCenterActivity.class);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("通知").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearView();
            }
        });
        setBarRightTx("清空");
        getBarRightRl().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearView();
            }
        });

        initListView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onMessageEvent(YunXinMessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        if (mAdapter!=null) {
            mAdapter.setChange(true);
        }
        initData();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_ADD_FRIEND) {
            showToast("已请求添加\""+name+"\"为好友");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_FRIEND);
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        mAdapter.clearData();
        mTouChuanOtherNoticeDao = MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao();
        mTouChuanOtherNotice = mTouChuanOtherNoticeDao.queryBuilder().build().list();
        mAdapter.addBody(mTouChuanOtherNotice);
        for (int i=0;i<mTouChuanOtherNotice.size();i++) {
            mTouChuanOtherNotice.get(i).setIsRead(1);
            mTouChuanOtherNoticeDao.update(mTouChuanOtherNotice.get(i));
        }
    }

    private void initListView() {
        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无通知");
        mEmpty.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT));
        mEmpty.setVisibility(View.GONE);
        ((ViewGroup)messagecenterListview.getParent()).addView(mEmpty);

        mAdapter = new MessageCenterAdapter(mContext, this);
        messagecenterListview.setEmptyView(mEmpty);
        messagecenterListview.setAdapter(mAdapter);
    }

    private void clearView() {
        PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
        builder.setMessage("确定清空通知消息？");
        builder.setPositiveButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mTouChuanOtherNoticeDao.deleteAll();
                MyBaseApplication.getApplication().getDaoSession().getTouChuanGiftNoticeDao().deleteAll();
                mAdapter.clearData();
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

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_messagecenter_cancel_bt:
                changDataBase(position);
                break;
            case R.id.item_messagecenter_ok_bt:
                changDataBase(position);
                OkHttpUtils.postString().url(Common.Url_Add_Friends+mAdapter.getItem(position).getSend_userId())
                        .content("").mediaType(Common.JSON)
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_ADD_FRIEND).id(Common.NET_ADD_FRIEND).build()
                        .execute(new MyStringCallback(mContext, this, true));
                break;
            case R.id.item_messagecenter_root_ll:
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getSend_userId()));
                break;
        }
    }

    private void changDataBase(int position) {
        mAdapter.clearUnreadNum(position);
        name = mAdapter.getItem(position).getSend_nickName();
        ueseID = mAdapter.getItem(position).getSend_userId();
        TouChuanOtherNotice temp = mTouChuanOtherNoticeDao.queryBuilder().where(TouChuanOtherNoticeDao.Properties.NoticeType.eq(1)
                , TouChuanOtherNoticeDao.Properties.Send_userId.eq(mAdapter.getItem(position).getSend_userId())
                , TouChuanOtherNoticeDao.Properties.Receive_userId.eq(mAdapter.getItem(position).getReceive_userId())).build().unique();
        temp.setIsEmbalmed(true);
        mTouChuanOtherNoticeDao.update(temp);
    }
}